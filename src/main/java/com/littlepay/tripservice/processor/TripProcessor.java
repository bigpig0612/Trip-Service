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
import java.util.HashMap;
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
        Map<String, List<Tab>> groupedTabList = tabList.stream()
                .collect(Collectors.groupingBy(tab ->
                                tab.getPAN() + "_" + tab.getCompanyId() + "_" + tab.getBusID(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Tab::getDateTimeUTC).reversed())
                                        .collect(Collectors.toList())
                        )
                ));

        List<Trip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tab>> entry:groupedTabList.entrySet()) {

            List<Tab> personTabList = entry.getValue();

            log.info("Processing group {}", entry.getKey());

            for(int i=0; i<personTabList.size(); i++) {
                //Each time, extract two records (current and previous) from the list and match them to determine whether the two records constitute a pair of ON and OFF events.
                Tab currentTab = personTabList.get(i);
                Tab previousTab = i+1>=personTabList.size()? null:personTabList.get(i+1);

                log.info("Current Tab: {}", currentTab);
                log.info("Previous Tab: {}", previousTab);

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

                trip.setChargeAmount(calculateChargeAmount(trip.getFromStopId(), trip.getToStopId(), trip.getStatus()));

                log.info("Trip created with information: {}", trip);

                if(trip.getStatus()== Trip.TripStatus.COMPLETED || trip.getStatus()== Trip.TripStatus.CANCELLED) i++;

                trips.add(trip);
            }
        }

        log.info("Trip calculation completed.....");

        return trips;
    }

    public void saveTripList(List<Trip> tripList) {
        tripService.saveTripList(tripList);
    }

    private double calculateChargeAmount(String tapOnStop, String tapOffStop, Trip.TripStatus tripStatus) {
        Map<String, Double> fareTable = new HashMap<>();
        fareTable.put("Stop1_Stop2", 3.25);
        fareTable.put("Stop2_Stop1", 3.25);
        fareTable.put("Stop2_Stop3", 5.50);
        fareTable.put("Stop3_Stop2", 5.50);
        fareTable.put("Stop1_Stop3", 7.30);
        fareTable.put("Stop3_Stop1", 7.30);

        switch (tripStatus) {
            case COMPLETED -> {
                return fareTable.get(tapOnStop + "_" + tapOffStop);
            }
            case INCOMPLETE -> {
                return Math.max(fareTable.getOrDefault(tapOnStop + "_Stop1",0.0),
                        Math.max(fareTable.getOrDefault(tapOnStop + "_Stop2", 0.0),
                                fareTable.getOrDefault(tapOnStop + "_Stop3", 0.0)));
            }
        }
        return 0;
    }

}
