package com.littlepay.tripservice.model;

import com.littlepay.tripservice.util.LocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tab {

    /**
     * The unique identifier for the tab.
     */
    @CsvBindByName(column = "ID")
    private Integer id;

    /**
     * The timestamp of the tab.
     */
    @CsvCustomBindByName(column = "DateTimeUTC", converter = LocalDateTimeConverter.class)
    private LocalDateTime dateTimeUTC;

    /**
     * The type of the tab.
     */
    @CsvBindByName(column = "TapType")
    private TabType tapType;

    /**
     * The ID of the stop where the tab occurred.
     */
    @CsvBindByName(column = "StopId")
    private String stopId;

    /**
     * The ID of the company operating the bus.
     */
    @CsvBindByName(column = "CompanyId")
    private String companyId;

    /**
     * The ID of the bus associated with the tab.
     */
    @CsvBindByName(column = "BusID")
    private String busID;

    /**
     * The PAN (Personal Account Number) of the customer associated with the tab.
     */
    @CsvBindByName(column = "PAN")
    private String PAN;

    /**
     * Enum representing the type of a tab, which can be ON or OFF.
     */
    public enum TabType {
        ON,
        OFF;
    }

}
