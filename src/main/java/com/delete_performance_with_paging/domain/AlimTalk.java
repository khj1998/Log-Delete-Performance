package com.delete_performance_with_paging.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalk {
    @Id
    private Long id;

    @Column(name = "message_type", nullable = false, length = 2)
    private String messageType;

    @Column(name = "sender_key", nullable = false, length = 40)
    private String senderKey;

    @Column(name = "template_code", nullable = false, length = 30)
    private String templateCode;

    @Column(name = "phone_number", nullable = false, length = 16)
    private String phoneNumber;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "req_date",nullable = false)
    private LocalDateTime reqDate;

    @Column(name = "client_id", length = 50)
    private String clientId;

    @Column(name = "title", length = 50)
    private String title;
}
