package com.littlepay.tripservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {

    private LocalDateTime started;

    private LocalDateTime finished;

    private long durationSecs;

    private String fromStopId;

    private String toStopId;

    private double chargeAmount;

    private String companyId;

    private String busID;

    private String PAN;

    private TripStatus status;

    public enum TripStatus {
        COMPLETED,
        INCOMPLETE,
        CANCELLED;
    }

}