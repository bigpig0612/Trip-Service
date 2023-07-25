package com.littlepay.tripservice.model;

import com.littlepay.tripservice.util.LocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;
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

    /**
     * The timestamp when the trip started.
     */
    @CsvCustomBindByName(column = "Started", converter = LocalDateTimeConverter.class)
    @CsvBindByPosition(position = 0)
    private LocalDateTime started;

    /**
     * The timestamp when the trip finished.
     */
    @CsvCustomBindByName(column = "Finished", converter = LocalDateTimeConverter.class)
    @CsvBindByPosition(position = 1)
    private LocalDateTime finished;

    /**
     * The duration of the trip in seconds.
     */
    @CsvBindByName(column = "DurationSecs")
    @CsvBindByPosition(position = 2)
    private long durationSecs;

    /**
     * The ID of the stop where the trip started.
     */
    @CsvBindByName(column = "FromStopId")
    @CsvBindByPosition(position = 3)
    private String fromStopId;

    /**
     * The ID of the stop where the trip finished.
     */
    @CsvBindByName(column = "ToStopId")
    @CsvBindByPosition(position = 4)
    private String toStopId;

    /**
     * The amount charged for the trip.
     */
    @CsvBindByName(column = "ChargeAmount")
    @CsvBindByPosition(position = 5)
    private double chargeAmount;

    /**
     * The ID of the company operating the bus.
     */
    @CsvBindByName(column = "CompanyId")
    @CsvBindByPosition(position = 6)
    private String companyId;

    /**
     * The ID of the bus used for the trip.
     */
    @CsvBindByName(column = "BusID")
    @CsvBindByPosition(position = 7)
    private String busID;

    /**
     * The PAN (Personal Account Number) of the customer.
     */
    @CsvBindByName(column = "PAN")
    @CsvBindByPosition(position = 8)
    private String PAN;

    /**
     * The status of the trip, which can be COMPLETED, INCOMPLETE, CANCELLED, or UNKNOWN.
     */
    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 9)
    private TripStatus status;

    /**
     * Enum representing the status of a trip.
     */
    public enum TripStatus {
        COMPLETED,
        INCOMPLETE,
        CANCELLED,
        UNKNOWN;
    }

}