package com.littlepay.tripservice.service;

import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.repository.TripRepository;
import com.littlepay.tripservice.strategy.FareCalculatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class for handling Trip-related operations.
 */
@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepo;

    private final FareCalculatorFactory fareCalculatorFactory;

    @Value("#{${application.config.trips.fareTable:{}}}")
    private Map<String, Double> fareTable;

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

    /**
     * Calculate the charge amount for a Trip based on tap on and tap off stops and trip status.
     *
     * @param tapOnStop   The stop where the trip started (tap on).
     * @param tapOffStop  The stop where the trip ended (tap off).
     * @param tripStatus  The status of the Trip (COMPLETED or INCOMPLETE).
     * @return The calculated charge amount.
     */
    public double calculateChargeAmount(String tapOnStop, String tapOffStop, Trip.TripStatus tripStatus) {
        return fareCalculatorFactory.getFareCalculator(tripStatus).calculateFare(tapOnStop, tapOffStop);
    }
}
