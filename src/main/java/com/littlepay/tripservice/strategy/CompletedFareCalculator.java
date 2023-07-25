package com.littlepay.tripservice.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * An implementation of the FareCalculator interface for complete trip
 */
@Component
public class CompletedFareCalculator implements FareCalculator {

    @Value("#{${application.config.trips.fareTable:{}}}")
    private Map<String, Double> fareTable;

    /**
     * Calculates the fare amount for a trip based on the provided tap-on and tap-off stops.
     *
     * @param tapOnStop  The ID of the stop tab on
     * @param tapOffStop The ID of the stop tab off
     * @return The calculated fare amount for the trip.
     */
    @Override
    public double calculateFare(String tapOnStop, String tapOffStop) {
        return fareTable.getOrDefault(tapOnStop + "_" + tapOffStop, 0.0);
    }
}