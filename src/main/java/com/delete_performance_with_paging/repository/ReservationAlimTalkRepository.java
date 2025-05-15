package com.delete_performance_with_paging.repository;

import com.delete_performance_with_paging.domain.ReservationAlimTalk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservationAlimTalkRepository extends JpaRepository<ReservationAlimTalk,Long> {
    @Query(value = "select r.id from ReservationAlimTalk r where r.reqDate < :reqDate")
    Page<Long> findReservationAlimTalkByOffset(@Param("reqDate") LocalDateTime reqDate, Pageable pageable);

    @Query(value = "select r.id from ReservationAlimTalk r where (:id is null or r.id > :id) " +
            "and r.reqDate < :reqDate order by r.id")
    Page<Long> findReservationAlimTalkByKeySet(@Param("id") Long id,@Param("reqDate") LocalDateTime reqDate,Pageable pageable);
}
