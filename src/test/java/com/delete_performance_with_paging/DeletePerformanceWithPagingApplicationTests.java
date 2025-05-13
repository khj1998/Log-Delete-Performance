package com.delete_performance_with_paging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
        String sql = "INSERT INTO ORDER_KAKAO_TALK(ID,message_type, sender_key, template_code, phone_number, message, req_date) "
                +"VALUES(:id,:messageType, :senderKey, :templateCode, :phoneNumber, :message, :reqDate)";
        int minusDays = 367;
        LocalDateTime toDeleteDate = LocalDateTime.now();
        Long id = 1L;

        for (int i = 1;i <= 365;i++) {
            List<MapSqlParameterSource> batchArgs = new ArrayList<>();

            for (int j = 0;j<ORDER_DAILY_RECORDS;j++) {
                MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("id",id+=1);
                mapSqlParameterSource.addValue("messageType", "AT");
                mapSqlParameterSource.addValue("senderKey", UUID.randomUUID().toString());
                mapSqlParameterSource.addValue("templateCode", "temp_code");
                mapSqlParameterSource.addValue("phoneNumber", "01011112222");
                mapSqlParameterSource.addValue("message", "message for testing alimtalk log deletion performance");
                mapSqlParameterSource.addValue("reqDate", toDeleteDate.minusDays(minusDays-(i+1)));

                batchArgs.add(mapSqlParameterSource);

                if (batchArgs.size() == BATCH_SIZE) {
                    namedParameterJdbcTemplate.batchUpdate(sql,batchArgs.toArray(new MapSqlParameterSource[0]));
                    batchArgs.clear();
                }
            }
        }
    }

    @Test
    void saveMockReservationAlimTalkData() {
        String sql = "INSERT INTO RESERVATION_KAKAO_TALK(ID,message_type, sender_key, template_code, phone_number, message, req_date) "
                +"VALUES(:id,:messageType, :senderKey, :templateCode, :phoneNumber, :message, :reqDate)";
        int minusDays = 367;
        LocalDateTime toDeleteDate = LocalDateTime.now();
        Long id = 1L;

        for (int i = 1;i <= 365;i++) {
            List<MapSqlParameterSource> batchArgs = new ArrayList<>();

            for (int j = 0; j<RESERVATION_DAILY_RECORDS;j++) {
                MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("id",id+=1);
                mapSqlParameterSource.addValue("messageType", "AT");
                mapSqlParameterSource.addValue("senderKey", UUID.randomUUID().toString());
                mapSqlParameterSource.addValue("templateCode", "temp_code");
                mapSqlParameterSource.addValue("phoneNumber", "01011112222");
                mapSqlParameterSource.addValue("message", "message for testing alimtalk log deletion performance");
                mapSqlParameterSource.addValue("reqDate", toDeleteDate.minusDays(minusDays-(i+1)));

                batchArgs.add(mapSqlParameterSource);

                if (batchArgs.size()==BATCH_SIZE) {
                    namedParameterJdbcTemplate.batchUpdate(sql,batchArgs.toArray(new MapSqlParameterSource[0]));
                    batchArgs.clear();
                }
            }
        }
    }
}
