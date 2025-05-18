package com.delete_performance_with_paging.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderAlimTalkRepositoryTest {
    @Autowired
    private OrderAlimTalkRepository orderAlimTalkRepository;

    @Test
    @DisplayName("실제 쿼리 확인용 테스트 코드")
    void findOrderAlimTalkByOffset() {
        Pageable pageable = PageRequest.of(0,1000);

        LocalDateTime deletionDate = LocalDateTime.now().minusYears(1);
        Slice<Long> alimTalkIdSlice = orderAlimTalkRepository.findOrderAlimTalkByOffset(deletionDate,pageable);

        assertEquals(alimTalkIdSlice.getContent().size(),1000);
    }
}