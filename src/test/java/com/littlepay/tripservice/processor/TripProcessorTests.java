package com.littlepay.tripservice.processor;

import com.littlepay.tripservice.service.TabService;
import com.littlepay.tripservice.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TripProcessorTests {

    @Mock
    private TabService tabService;
    @Mock
    private TripService tripService;
    @InjectMocks
    private TripProcessor processor;

    @Test
    void processTrip() {
    }
}