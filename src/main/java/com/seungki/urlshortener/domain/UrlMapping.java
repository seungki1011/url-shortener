package com.seungki.urlshortener.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "url_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlMapping {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortcode;

    @Column(nullable = false)
    private String originalUrl;

    private LocalDateTime createdAt;
    private LocalDateTime viewedAt;
    private int viewCount = 0;

    public UrlMapping(String shortcode, String originalUrl, LocalDateTime createdAt) {
        this.shortcode = shortcode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }

    public void incrementViewCount() {
        this.viewCount++;
        this.viewedAt = LocalDateTime.now();
    }

    /*
      추후 회원 가입 기능을 추가할 때 사용
     */
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id")
    // private Member member;

}