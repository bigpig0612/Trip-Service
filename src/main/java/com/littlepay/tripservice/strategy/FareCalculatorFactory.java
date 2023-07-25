package com.littlepay.tripservice.strategy;

import com.littlepay.tripservice.model.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A factory class that provides a way to obtain the appropriate FareCalculator based on the Trip Status.
 */
@Component
@RequiredArgsConstructor
public class FareCalculatorFactory {

    private final CompletedFareCalculator completedFareCalculator;
    private final IncompleteFareCalculator incompleteFareCalculator;
    private final DefaultFareCalculator defaultFareCalculator;

    /**
     * Gets the appropriate FareCalculator based on the given TripStatus.
     *
     * @param tripStatus The status of the trip, which can be COMPLETED, INCOMPLETE, or any other status.
     * @return The FareCalculator instance corresponding to the provided trip status. If the trip status
     * is COMPLETED, the completedFareCalculator is returned. If the trip status is INCOMPLETE,
     * the incompleteFareCalculator is returned. For any other trip status, the defaultFareCalculator
     * is returned.
     */
    public FareCalculator getFareCalculator(Trip.TripStatus tripStatus) {
        switch (tripStatus) {
            case COMPLETED:
                return completedFareCalculator;
            case INCOMPLETE:
                return incompleteFareCalculator;
            default:
                return defaultFareCalculator;
        }
    }
}