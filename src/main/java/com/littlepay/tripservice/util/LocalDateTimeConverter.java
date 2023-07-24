package com.littlepay.tripservice.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom field converter for OpenCSV, used to convert String type time data to LocalDateTime type.
 */
public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Convert the String type time data to LocalDateTime type.
     *
     * @param value String type time data
     * @return LocalDateTime type time data
     */
    @Override
    protected Object convert(String value) {
        return LocalDateTime.parse(value.trim(), formatter);
    }
}