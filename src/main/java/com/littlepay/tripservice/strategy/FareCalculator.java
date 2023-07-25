package com.littlepay.tripservice.strategy;

/**
 * The FareCalculator interface represents a strategy for calculating the fare
 * amount based on the tap-on and tap-off stops of a trip.
 */
public interface FareCalculator {

    /**
     * Calculates the fare amount for a trip based on the provided tap-on and tap-off stops.
     *
     * @param tapOnStop  The ID of the stop tab on
     * @param tapOffStop The ID of the stop tab off
     * @return The calculated fare amount for the trip.
     */
    double calculateFare(String tapOnStop, String tapOffStop);

}
