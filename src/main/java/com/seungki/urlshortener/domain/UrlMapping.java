package com.seungki.urlshortener.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlMapping {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String hashValue;

    @Column(nullable = false)
    private String originalUrl;

    private LocalDateTime createdAt;
    private LocalDateTime viewedAt;
    private int viewCount;

    /*
      추후 회원 가입 기능을 추가할 때 사용
     */
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "MEMBER_ID")
    // private Member member;

}
