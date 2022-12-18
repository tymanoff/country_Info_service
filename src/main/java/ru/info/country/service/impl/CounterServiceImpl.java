package ru.info.country.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.info.country.repository.SuccessResponseCountRepository;
import ru.info.country.service.CounterService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void increment(@NonNull String service, @NonNull Integer version, boolean cached) {

        List<Map<String, Object>> records = jdbcTemplate
                .queryForList(SuccessResponseCountRepository.SELECT_ROW_FOR_UPDATE, service, version);

        if (CollectionUtils.isEmpty(records)) {
            final int cachedSuccessResponseCount;
            final int paidSuccessResponseCount;
            if (cached) {
                cachedSuccessResponseCount = 1;
                paidSuccessResponseCount = 0;
            } else {
                cachedSuccessResponseCount = 0;
                paidSuccessResponseCount = 1;
            }
            jdbcTemplate.update(SuccessResponseCountRepository.INSERT_NEW_ROW,
                    service, version, cachedSuccessResponseCount, paidSuccessResponseCount);
        } else {
            Map<String, Object> foundRecord = records.get(0);
            UUID id = (UUID) foundRecord.get("id");

            if (cached) {
                int cachedSuccessResponseCount = (int) foundRecord.get("cached_success_response_count");
                jdbcTemplate.update(SuccessResponseCountRepository.UPDATE_INCREMENT_CASHED_ROW,
                        ++cachedSuccessResponseCount, id);
            } else {
                int paidSuccessResponseCount = (int) foundRecord.get("paid_success_response_count");
                jdbcTemplate.update(SuccessResponseCountRepository.UPDATE_INCREMENT_PAID_ROW,
                        ++paidSuccessResponseCount, id);
            }
        }
    }
}
