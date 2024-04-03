package com.group.petSitter.domain.user.service;

import com.group.petSitter.domain.user.UserGrade;
import com.group.petSitter.domain.user.repository.UserRepository;
import com.group.petSitter.domain.user.repository.response.UserOrderCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {

    private static final int ONE = 1;

    private final UserRepository userRepository;

    private LocalDateTime getStartTimeOfPreviousMonth() {
        return YearMonth.now()
            .minusMonths(ONE)
            .atDay(ONE)
            .atStartOfDay();
    }

    private LocalDateTime getEndTimeOfPreviousMonth() {
        return YearMonth.now()
            .atDay(ONE)
            .atStartOfDay()
            .minusNanos(ONE);
    }

    /*
        private Map<UserGrade, List<UserOrderCount>> groupByUserGrade(
        List<UserOrderCount> userOrderCounts) {
        return userOrderCounts.stream()
            .collect(groupingBy(userOrderCount ->
                UserGrade.calculateUserGrade(userOrderCount.getOrderCount())));
    }*/

    private List<Long> extractUserIds(List<UserOrderCount> userOrderCountGroup) {
        return userOrderCountGroup.stream()
            .map(UserOrderCount::getUserId)
            .toList();
    }
}
