package com.littlepay.tripservice.repository;

import com.littlepay.tripservice.model.Trip;
import com.littlepay.tripservice.util.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository class for handling Trip data.
 */
@Component
@RequiredArgsConstructor
public class TripRepository {

    private final CsvHelper csvHelper;

    /**
     * The file path to the CSV file where Trip data is stored.
     */
    @Value("${application.config.trips.filePath}")
    private String filePath;

    /**
     * The header string to be used when saving Trip data to CSV.
     */
    @Value("${application.config.trips.header}")
    private String header;

    /**
     * Save a list of Trip objects to the CSV file.
     *
     * @param tripList the list of Trip objects to be saved
     */
    public void saveTripList(List<Trip> tripList) {
        csvHelper.writeListToCSV(tripList, filePath,header);
    }

    /**
     * Save a single Trip object to the CSV file.
     *
     * @param trip the Trip object to be saved
     */
    public void saveTrip(Trip trip) {
        csvHelper.appendToCSV(trip, filePath,header);
    }

}
