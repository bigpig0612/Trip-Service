package com.littlepay.tripservice.service;

import com.littlepay.tripservice.model.Tab;
import com.littlepay.tripservice.repository.TabRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TabServiceTests {

    @Mock
    private TabRepository tabRepo;
    @InjectMocks
    private TabService tabService;

    @Test
    @DisplayName("Test get all tabs")
    void getAllTabs() {
        List<Tab> tabList = Arrays.asList(
                new Tab(1, LocalDateTime.of(2023, 1, 22, 13, 0, 0), Tab.TabType.ON, "Stop1", "Company1", "Bus37", "5500005555555559"),
                new Tab(2, LocalDateTime.of(2023, 1, 22, 13, 5, 0), Tab.TabType.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"),
                new Tab(3, LocalDateTime.of(2023, 1, 22, 9, 20, 0), Tab.TabType.ON, "Stop3", "Company1", "Bus36", "4111111111111111"),
                new Tab(4, LocalDateTime.of(2023, 1, 23, 8, 0, 0), Tab.TabType.ON, "Stop1", "Company1", "Bus37", "4111111111111111"),
                new Tab(5, LocalDateTime.of(2023, 1, 23, 8, 2, 0), Tab.TabType.OFF, "Stop1", "Company1", "Bus37", "4111111111111111"),
                new Tab(6, LocalDateTime.of(2023, 1, 24, 16, 30, 0), Tab.TabType.OFF, "Stop2", "Company1", "Bus37", "5500005555555559")
        );

        when(tabService.getAllTabs()).thenReturn(tabList);

        assertEquals(tabList, tabService.getAllTabs());
    }
}