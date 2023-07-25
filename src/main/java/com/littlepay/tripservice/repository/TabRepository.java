package com.littlepay.tripservice.repository;

import com.littlepay.tripservice.model.Tab;
import com.littlepay.tripservice.util.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository class for handling Tab data.
 */
@Component
@RequiredArgsConstructor
public class TabRepository {

    private final CsvHelper csvHelper;

    /**
     * The file path to the CSV file where Tab data is stored.
     */
    @Value("${application.config.taps.filePath}")
    private String filePath;

    /**
     * The header string to be used when saving Tab data to CSV.
     */
    @Value("${application.config.taps.header}")
    private String header;

    /**
     * Retrieves all the tab records from the CSV file.
     *
     * @return a list of Tab objects containing the tab records.
     */
    public List<Tab> getAllTabs() {
        return csvHelper.readCsvToList(filePath, Tab.class);
    }
}
