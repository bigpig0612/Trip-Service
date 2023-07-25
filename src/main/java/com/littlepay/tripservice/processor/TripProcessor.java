package com.littlepay.tripservice.processor;

import com.littlepay.tripservice.model.Tab;
import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.service.TabService;
import com.littlepay.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The TripProcessor is responsible for processing trip information based on tab history.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TripProcessor {

    private final TabService tabService;
    private final TripService tripService;

    /**
     * Generate trips and save them to the repository.
     * It retrieves all Tab information from TabService and calculates trip information based rules.
     *
     * @return a list of Trip objects.
     */
    public List<Trip> createTripList() {
        log.info("Starting trip calculation...");
        List<Tab> tabList = tabService.getAllTabs();

        //grouping by PAN_CompanyID_BusID
        Map<String, List<Tab>> groupedTabList = groupAndSortTabsByKeys(tabList);

        List<Trip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tab>> entry:groupedTabList.entrySet()) {

            List<Tab> personTabList = entry.getValue();

            log.info("Processing group {}", entry.getKey());

            for(int i=0; i<personTabList.size(); i++) {
                //Each time, extract two records (current and previous) from the list and match them to determine whether the two records constitute a pair of ON and OFF events.
                Tab currentTab = personTabList.get(i);
                Tab previousTab = i+1>=personTabList.size()? null:personTabList.get(i+1);

                Trip trip = createTripFromTabPair(currentTab, previousTab);


                if(trip.getStatus()== Trip.TripStatus.COMPLETED || trip.getStatus()== Trip.TripStatus.CANCELLED) i++;

                trips.add(trip);
            }
        }

        log.info("Trip calculation completed.....");

        return trips;
    }

    /**
     * Save the list of trips to the repository.
     *
     * @param tripList The list of Trip objects to be saved.
     */
    public void saveTripList(List<Trip> tripList) {
        tripService.saveTripList(tripList);
    }

    /**
     * Create a Trip object from a pair of Tab objects.
     *
     * @param currentTab  The current Tab object.
     * @param previousTab The previous Tab object.
     * @return The generated Trip object.
     */
    private Trip createTripFromTabPair(Tab currentTab, Tab previousTab) {
        log.info("Creating trip with current Tab: {}, previous Tab: {}", currentTab, previousTab);

        Trip trip;
        Tab.TabType currentTabType = currentTab.getTapType();

        if(currentTabType==Tab.TabType.OFF) {
            if(previousTab==null || previousTab.getTapType()==Tab.TabType.OFF) {
                //Scenario 1: If the currentTab status is OFF, and the previous status is also OFF or object is null, the currentTab alone constitutes a status of UN_KNOWN.
                trip = Trip.builder().finished(currentTab.getDateTimeUTC())
                        .toStopId(currentTab.getStopId()).companyId(currentTab.getCompanyId())
                        .busID(currentTab.getBusID()).PAN(currentTab.getPAN()).status(Trip.TripStatus.UNKNOWN).build();
            } else {
                //Scenario 2: If the currentTab status is OFF, and the previousTab status is ON, the currentTab and previousTab together constitute a status of COMPLETE or CANCEL, depending on whether they have the same stop or not.
                long durationSecs = Duration.between(previousTab.getDateTimeUTC(), currentTab.getDateTimeUTC()).getSeconds();
                boolean isSameStop = currentTab.getStopId().equals(previousTab.getStopId());

                trip = Trip.builder().started(previousTab.getDateTimeUTC()).finished(currentTab.getDateTimeUTC())
                        .durationSecs(durationSecs )
                        .fromStopId(previousTab.getStopId())
                        .toStopId(currentTab.getStopId())
                        .companyId(currentTab.getCompanyId())
                        .busID(currentTab.getBusID())
                        .status(isSameStop ? Trip.TripStatus.CANCELLED : Trip.TripStatus.COMPLETED)
                        .PAN(currentTab.getPAN()).build();
            }
        } else {
            //Scenario 3: If the currentTab status is ON, regardless of the previousTab status, the currentTab alone constitutes a status of INCOMPLETE for the Trip.
            trip = Trip.builder().started(currentTab.getDateTimeUTC())
                    .fromStopId(currentTab.getStopId())
                    .companyId(currentTab.getCompanyId())
                    .busID(currentTab.getBusID())
                    .PAN(currentTab.getPAN())
                    .status(Trip.TripStatus.INCOMPLETE).build();
        }

        trip.setChargeAmount(tripService.calculateChargeAmount(trip.getFromStopId(), trip.getToStopId(), trip.getStatus()));

        log.info("Trip created with information: {}", trip);
        return trip;
    }

    /**
     * Group and sort the list of Tab objects based on PAN_CompanyID_BusID keys. The value will be sorted by time
     *
     * @param tabList The list of Tab objects to be grouped and sorted.
     * @return A map with keys as PAN_CompanyID_BusID and values as corresponding lists of Tab objects.
     */
    private Map<String, List<Tab>> groupAndSortTabsByKeys(List<Tab> tabList) {
        return tabList.stream()
                .collect(Collectors.groupingBy(tab ->
                                tab.getPAN() + "_" + tab.getCompanyId() + "_" + tab.getBusID(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Tab::getDateTimeUTC).reversed())
                                        .collect(Collectors.toList())
                        )
                ));
    }

}
