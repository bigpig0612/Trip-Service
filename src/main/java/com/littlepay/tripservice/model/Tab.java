package com.littlepay.tripservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tab {

    private Integer id;

    private LocalDateTime dateTimeUTC;

    private TabType tapType;

    private String stopId;

    private String companyId;

    private String busID;

    private String PAN;

    public enum TabType {
        ON,
        OFF;
    }

}
