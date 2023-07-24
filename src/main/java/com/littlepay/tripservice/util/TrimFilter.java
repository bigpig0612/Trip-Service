package com.littlepay.tripservice.util;

import com.opencsv.bean.CsvToBeanFilter;

/**
 * Custom filter for OpenCSV, used to trim leading and trailing spaces from each column in the CSV line.
 */
public class TrimFilter implements CsvToBeanFilter {

    /**
     * Filter method to trim leading and trailing spaces from each column in the CSV line.
     *
     * @param line the CSV line as an array of String
     * @return true to allow all lines to be processed
     */
    @Override
    public boolean allowLine(String[] line)  {
        // Trim leading and trailing spaces from each column
        for (int i = 0; i < line.length; i++) {
            line[i] = line[i].trim();
        }
        return true; // Allow all lines to be processed
    }
}
