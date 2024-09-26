package com.seungki.urlshortener.web.dto;

import com.seungki.urlshortener.common.domain.UrlMapping;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UrlDetailResponse {

    private String shortcode;
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime viewedAt;
    private int viewCount;

    public UrlDetailResponse(UrlMapping urlMapping) {
        shortcode = urlMapping.getShortcode();
        originalUrl = urlMapping.getOriginalUrl();
        createdAt = urlMapping.getCreatedAt();
        viewedAt = urlMapping.getViewedAt();
        viewCount = urlMapping.getViewCount();
    }

}