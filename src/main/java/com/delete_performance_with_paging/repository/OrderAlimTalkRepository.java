package com.delete_performance_with_paging.repository;

import com.delete_performance_with_paging.domain.OrderAlimTalk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderAlimTalkRepository extends JpaRepository<OrderAlimTalk,Long> {
    @Query(value = "select o.id from OrderAlimTalk o where o.reqDate < :reqDate")
    Slice<Long> findOrderAlimTalkByOffset(@Param("reqDate")LocalDateTime reqDate, Pageable pageable);

    @Query(value = "select o.id from OrderAlimTalk o where (:id is null or o.id <= :id) " +
            "and o.reqDate < :reqDate order by o.id")
    Slice<Long> findOrderAlimTalkByKeySet(@Param("id") Long id,@Param("reqDate") LocalDateTime reqDate,Pageable pageable);
}
