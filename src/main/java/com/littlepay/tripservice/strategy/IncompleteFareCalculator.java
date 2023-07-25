package com.littlepay.tripservice.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * An implementation of the FareCalculator interface for incomplete trip
 */
@Component
public class IncompleteFareCalculator implements FareCalculator {

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
        return Math.max(
                fareTable.getOrDefault(tapOnStop + "_Stop1", 0.0),
                Math.max(fareTable.getOrDefault(tapOnStop + "_Stop2", 0.0), fareTable.getOrDefault(tapOnStop + "_Stop3", 0.0))
        );
    }
}
