package com.hdiary.backend.service;

import com.hdiary.backend.dto.response.MoodCountProjection;
import com.hdiary.backend.dto.response.MoodStatResponseDto;
import com.hdiary.backend.dto.response.StatisticsResponseDto;
import com.hdiary.backend.entity.enums.Mood;
import com.hdiary.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public StatisticsResponseDto getMoodStatistics(Long userId) {
        LocalDate today = LocalDate.now();
        
        // Daily range
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        
        // Weekly range
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);

        List<MoodCountProjection> dailyProjections = postRepository.countMoodsByUserIdAndDateRange(userId, startOfDay, endOfDay);
        List<MoodCountProjection> weeklyProjections = postRepository.countMoodsByUserIdAndDateRange(userId, startOfWeek, endOfWeek);
        
        return new StatisticsResponseDto(
                mapToDto(dailyProjections),
                mapToDto(weeklyProjections)
        );
    }
    
    private List<MoodStatResponseDto> mapToDto(List<MoodCountProjection> projections) {
        Map<Mood, Long> countMap = projections.stream()
                .collect(Collectors.toMap(MoodCountProjection::getMood, MoodCountProjection::getCount));

        return Arrays.stream(Mood.values())
                .map(mood -> new MoodStatResponseDto(
                        mood.name(),
                        mood.getEmoji(),
                        countMap.getOrDefault(mood, 0L)
                ))
                .collect(Collectors.toList());
    }
}
