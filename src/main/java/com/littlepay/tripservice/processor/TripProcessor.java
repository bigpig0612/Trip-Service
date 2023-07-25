package com.littlepay.tripservice.processor;

import com.littlepay.tripservice.service.TabService;
import com.littlepay.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
     * Calculate trips and save them to the repository.
     * It retrieves all Tab information from TabService and calculates trip information based rules.
     */
    public void processTrip() {

    }

}
