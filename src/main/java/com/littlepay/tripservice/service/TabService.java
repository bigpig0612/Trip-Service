package com.littlepay.tripservice.service;

import com.littlepay.tripservice.repository.TabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TabService {

    private final TabRepository tabRepo;

}
