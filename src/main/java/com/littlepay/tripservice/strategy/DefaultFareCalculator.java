package com.littlepay.tripservice.strategy;

import org.springframework.stereotype.Component;

/**
 * A default implementation of the FareCalculator interface that calculates the fare for a trip
 */
@Component
public class DefaultFareCalculator implements FareCalculator{

    /**
     * Calculates the fare amount for a trip based on the provided tap-on and tap-off stops.
     *
     * @param tapOnStop  The ID of the stop tab on
     * @param tapOffStop The ID of the stop tab off
     * @return The calculated fare amount for the trip.
     */
    @Override
    public double calculateFare(String tapOnStop, String tapOffStop) {
        return 0;
    }
}
