package com.delete_performance_with_paging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class DeletePerformanceWithPagingApplicationTests {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final int BATCH_SIZE = 1_000;
    private final int ORDER_DAILY_RECORDS = 75_000;
    private final int RESERVATION_DAILY_RECORDS = 25_000;

    @Test
    void saveMockOrderAlimTalkData() {
        StringBuilder sql = new StringBuilder("INSERT INTO ORDER_KAKAO_TALK (ID, message_type, sender_key, template_code, phone_number, message, req_date) VALUES ");
        int minusDays = 367;
        LocalDateTime toDeleteDate = LocalDateTime.now();
        Long id = 1L;
        List<Object> params = new ArrayList<>();

        for (int i = 1; i <= 365; i++) {
            List<String> valueClauses = new ArrayList<>();
            params.clear();

            for (int j = 0; j < ORDER_DAILY_RECORDS; j++) {
                valueClauses.add("(?, ?, ?, ?, ?, ?, ?)");
                params.add(id++);
                params.add("AT");
                params.add(UUID.randomUUID().toString());
                params.add("temp_code");
                params.add("01011112222");
                params.add("message for testing alimtalk log deletion performance");
                params.add(Timestamp.valueOf(toDeleteDate.minusDays(minusDays - (i + 1))));

                if (valueClauses.size() == BATCH_SIZE) {
                    String finalSql = sql + String.join(",", valueClauses);
                    namedParameterJdbcTemplate.getJdbcTemplate().update(finalSql, params.toArray());
                    valueClauses.clear();
                    params.clear();
                }
            }

            if (!valueClauses.isEmpty()) {
                String finalSql = sql + String.join(",", valueClauses);
                namedParameterJdbcTemplate.getJdbcTemplate().update(finalSql, params.toArray());
            }
        }
    }

    @Test
    void saveMockReservationAlimTalkData() {
        StringBuilder sql = new StringBuilder("INSERT INTO RESERVATION_KAKAO_TALK (ID, message_type, sender_key, template_code, phone_number, message, req_date) VALUES ");
        int minusDays = 367;
        LocalDateTime toDeleteDate = LocalDateTime.now();
        Long id = 1L;
        List<Object> params = new ArrayList<>();

        for (int i = 1; i <= 365; i++) {
            List<String> valueClauses = new ArrayList<>();
            params.clear();

            for (int j = 0; j < RESERVATION_DAILY_RECORDS; j++) {
                valueClauses.add("(?, ?, ?, ?, ?, ?, ?)");
                params.add(id++);
                params.add("AT");
                params.add(UUID.randomUUID().toString());
                params.add("temp_code");
                params.add("01011112222");
                params.add("message for testing alimtalk log deletion performance");
                params.add(Timestamp.valueOf(toDeleteDate.minusDays(minusDays - (i + 1))));

                if (valueClauses.size() == BATCH_SIZE) {
                    String finalSql = sql + String.join(",", valueClauses);
                    namedParameterJdbcTemplate.getJdbcTemplate().update(finalSql, params.toArray());
                    valueClauses.clear();
                    params.clear();
                }
            }

            if (!valueClauses.isEmpty()) {
                String finalSql = sql + String.join(",", valueClauses);
                namedParameterJdbcTemplate.getJdbcTemplate().update(finalSql, params.toArray());
            }
        }
    }
}
