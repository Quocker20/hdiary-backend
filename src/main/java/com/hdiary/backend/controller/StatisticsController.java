package com.hdiary.backend.controller;

import com.hdiary.backend.dto.response.StatisticsResponseDto;
import com.hdiary.backend.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/mood")
    public ResponseEntity<StatisticsResponseDto> getMoodStatistics() {
        log.info("Received request to get global mood statistics");
        return ResponseEntity.ok(statisticsService.getMoodStatistics());
    }
}
