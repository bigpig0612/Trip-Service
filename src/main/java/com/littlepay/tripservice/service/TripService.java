package com.littlepay.tripservice.service;

import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling Trip-related operations.
 */
@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepo;

    /**
     * Save a list of Trip objects.
     *
     * @param tripList the list of Trip objects to be saved
     */
    public void saveTripList(List<Trip> tripList) {
        tripRepo.saveTripList(tripList);
    }

    /**
     * Save a single Trip object.
     *
     * @param trip the Trip object to be saved
     */
    public void saveTrip(Trip trip) {
        tripRepo.saveTrip(trip);
    }
}
