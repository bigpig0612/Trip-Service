package com.littlepay.tripservice.service;

import com.littlepay.tripservice.model.Tab;
import com.littlepay.tripservice.repository.TabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling Tab-related operations.
 */
@Service
@RequiredArgsConstructor
public class TabService {

    private final TabRepository tabRepo;

    /**
     * Retrieves all the tab records.
     *
     * @return a list of Tab objects containing the tab records.
     */
    public List<Tab> getAllTabs() {
        return tabRepo.getAllTabs();
    }

}
