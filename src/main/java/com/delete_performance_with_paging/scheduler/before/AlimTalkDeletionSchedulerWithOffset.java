package com.delete_performance_with_paging.scheduler.before;

import com.delete_performance_with_paging.repository.OrderAlimTalkRepository;
import com.delete_performance_with_paging.repository.ReservationAlimTalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlimTalkDeletionSchedulerWithOffset {
    private final OrderAlimTalkRepository orderAlimTalkRepository;
    private final ReservationAlimTalkRepository reservationAlimTalkRepository;

    private final int ELEMENT_SIZE = 1000;

    @Scheduled(fixedDelay = Long.MAX_VALUE,initialDelay = 2000)
    public void deleteLogWithOffset() {
        log.info("[ Scheduler ] AlimTalkDeletionScheduler has been started");
        Pageable pageable = PageRequest.of(0,ELEMENT_SIZE);

        LocalDateTime deletionDate = LocalDateTime.now().minusYears(1);
        Slice<Long> orderAlimTalkIdSlice = orderAlimTalkRepository.findOrderAlimTalkByOffset(deletionDate,pageable);

        int orderAlimTalkDeleteCount = 0;
        while (!orderAlimTalkIdSlice.getContent().isEmpty()) {
            orderAlimTalkRepository.deleteAllByIdInBatch(orderAlimTalkIdSlice.getContent());
            orderAlimTalkDeleteCount += orderAlimTalkIdSlice.getSize();
            orderAlimTalkIdSlice = orderAlimTalkRepository.findOrderAlimTalkByOffset(deletionDate,pageable);
        }

        Slice<Long> reservationAlimTalkIdSlice = reservationAlimTalkRepository.findReservationAlimTalkByOffset(deletionDate,pageable);

        int reservationAlimTalkCount = 0;
        while (!reservationAlimTalkIdSlice.getContent().isEmpty()) {
            reservationAlimTalkRepository.deleteAllByIdInBatch(reservationAlimTalkIdSlice.getContent());
            reservationAlimTalkCount += reservationAlimTalkIdSlice.getSize();
            reservationAlimTalkIdSlice = reservationAlimTalkRepository.findReservationAlimTalkByOffset(deletionDate,pageable);
        }

        log.info("[ Scheduler ] AlimTalkDeletionScheduler has been ended, deleted count - ORDER : {}, RESERVATION : {}"
                ,orderAlimTalkDeleteCount,reservationAlimTalkCount);
    }
}
