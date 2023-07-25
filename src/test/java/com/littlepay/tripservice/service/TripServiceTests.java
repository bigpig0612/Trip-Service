package com.littlepay.tripservice.service;

import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TripServiceTests  {

    @Mock
    private TripRepository tripRepo;
    @InjectMocks
    private TripService tripService;

    @Test
    @DisplayName("Test save trips")
    public void testSaveTripList() {
        List<Trip> tripList = new ArrayList<>();
        Trip trip1 = new Trip();
        trip1.setStarted(LocalDateTime.now());
        trip1.setFinished(LocalDateTime.now().plusMinutes(30));

        tripService.saveTripList(tripList);

        verify(tripRepo, times(1)).saveTripList(tripList);
    }

    @Test
    @DisplayName("Test single trip")
    public void testSaveTrip() {
        Trip trip = new Trip();
        trip.setStarted(LocalDateTime.now());
        trip.setFinished(LocalDateTime.now().plusMinutes(30));

        tripService.saveTrip(trip);

        verify(tripRepo, times(1)).saveTrip(trip);
    }

}
