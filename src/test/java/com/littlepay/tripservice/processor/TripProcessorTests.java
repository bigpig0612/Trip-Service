package com.littlepay.tripservice.processor;

import com.littlepay.tripservice.model.Tab;
import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.service.TabService;
import com.littlepay.tripservice.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripProcessorTests {

    @Mock
    private TabService tabService;
    @Mock
    private TripService tripService;

    @InjectMocks
    private TripProcessor tripProcessor;

    @Test
    void createTripList() {
        List<Tab> tabList = getTabList();

        when(tabService.getAllTabs()).thenReturn(tabList);

        List<Trip> expectedTripList = getTripList();

        assertIterableEquals(expectedTripList, tripProcessor.createTripList());
    }

    @Test
    void saveTripList() {
        List<Trip> tripList = getTripList();

        tripProcessor.saveTripList(tripList);

        verify(tripService).saveTripList(tripList);
    }

    private static List<Tab> getTabList() {
        List<Tab> tabList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        tabList.add(new Tab(1, LocalDateTime.parse("22-01-2023 13:00:00", formatter), Tab.TabType.ON, "Stop1", "Company1", "Bus37", "5500005555555559"));
        tabList.add(new Tab(2, LocalDateTime.parse("22-01-2023 13:05:00", formatter), Tab.TabType.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"));
        tabList.add(new Tab(3, LocalDateTime.parse("22-01-2023 09:20:00", formatter), Tab.TabType.ON, "Stop3", "Company1", "Bus36", "4111111111111111"));
        tabList.add(new Tab(4, LocalDateTime.parse("23-01-2023 08:00:00", formatter), Tab.TabType.ON, "Stop1", "Company1", "Bus37", "4111111111111111"));
        tabList.add(new Tab(5, LocalDateTime.parse("23-01-2023 08:02:00", formatter), Tab.TabType.OFF, "Stop1", "Company1", "Bus37", "4111111111111111"));
        tabList.add(new Tab(6, LocalDateTime.parse("24-01-2023 16:30:00", formatter), Tab.TabType.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"));

        return tabList;
    }

    private static List<Trip> getTripList() {
        List<Trip> tripList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        tripList.add(Trip.builder()
                .started(null)
                .finished(LocalDateTime.parse("2023-01-24T16:30", formatter))
                .durationSecs(0)
                .fromStopId(null)
                .toStopId("Stop2")
                .chargeAmount(0.0)
                .companyId("Company1")
                .busID("Bus37")
                .PAN("5500005555555559")
                .status(Trip.TripStatus.UNKNOWN)
                .build());

        tripList.add(Trip.builder()
                .started(LocalDateTime.parse("2023-01-22T13:00", formatter))
                .finished(LocalDateTime.parse("2023-01-22T13:05", formatter))
                .durationSecs(300)
                .fromStopId("Stop1")
                .toStopId("Stop2")
                .chargeAmount(3.25)
                .companyId("Company1")
                .busID("Bus37")
                .PAN("5500005555555559")
                .status(Trip.TripStatus.COMPLETED)
                .build());

        tripList.add(Trip.builder()
                .started(LocalDateTime.parse("2023-01-22T09:20", formatter))
                .finished(null)
                .durationSecs(0)
                .fromStopId("Stop3")
                .toStopId(null)
                .chargeAmount(7.3)
                .companyId("Company1")
                .busID("Bus36")
                .PAN("4111111111111111")
                .status(Trip.TripStatus.INCOMPLETE)
                .build());

        tripList.add(Trip.builder()
                .started(LocalDateTime.parse("2023-01-23T08:00", formatter))
                .finished(LocalDateTime.parse("2023-01-23T08:02", formatter))
                .durationSecs(120)
                .fromStopId("Stop1")
                .toStopId("Stop1")
                .chargeAmount(0.0)
                .companyId("Company1")
                .busID("Bus37")
                .PAN("4111111111111111")
                .status(Trip.TripStatus.CANCELLED)
                .build());
        return tripList;
    }
}