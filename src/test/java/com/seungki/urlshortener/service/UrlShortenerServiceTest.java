package com.seungki.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.seungki.urlshortener.ssr.domain.UrlMapping;
import com.seungki.urlshortener.ssr.repository.UrlMappingRepository;
import com.seungki.urlshortener.ssr.service.UrlShortenerService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UrlShortenerServiceTest {

    @Autowired
    private UrlMappingRepository umr;
    @Autowired
    private UrlShortenerService uss;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void initData() {
        uss.shortenUrl("https://www.naver.com/");
        uss.shortenUrl("https://www.google.com");
    }

    @DisplayName("원본URL을 입력으로 주면 (숏코드, 원본URL, 생성시간)이 들어간 튜플이 존재해야한다")
    @Test
    public void testShortenUrl() {

        String originalUrl = "https://www.youtube.com/";
        String shortcode = uss.shortenUrl(originalUrl);
        LocalDateTime now = LocalDateTime.now();

        UrlMapping urlMapping = umr.findByShortCode(shortcode).get();

        long secondsDifference = ChronoUnit.SECONDS.between(urlMapping.getCreatedAt(), now);

        assertNotNull(urlMapping);
        assertThat(urlMapping.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(urlMapping.getShortcode()).isEqualTo(shortcode);
        assertTrue(secondsDifference < 1);

    }

    @DisplayName("중복된 URL로 엔티티를 입력하는 경우 솔트를 추가해서 숏코드를 재생성한다")
    @Test
    public void testDuplicateShortcode() {

        String originalUrl = "https://www.inflearn.com/";

        String originalShortcode = uss.shortenUrl(originalUrl);
        String saltUrlShortcode = uss.shortenUrl(originalUrl);

        assertThat(originalShortcode).isNotEqualTo(saltUrlShortcode);
    }

    @DisplayName("qV4ClXW -> https://www.naver.com/")
    @Test
    public void testFindOriginalUrl() {

        String shortcode = "qV4ClXW";
        String originalUrl = "https://www.naver.com/";

        String findOriginalUrl = uss.findOriginalUrl(shortcode);

        assertNotNull(findOriginalUrl);
        assertThat(findOriginalUrl).isEqualTo(originalUrl);

    }

    @DisplayName("존재하지 않는 숏코드로 원본 URL을 찾을시 null 반환")
    @Test
    public void testNullForNonExistingShortcode() {

        String nonExistingShortcode = "abcd123";

        String findUrl = uss.findOriginalUrl(nonExistingShortcode);

        assertNull(findUrl);

    }

    @DisplayName("findOriginalUrl()로 성공적으로 조회시 viewCount 증가, viewedAt 업데이트")
    @Test
    public void testIncrementViewCount() {

        String shortcode = "qV4ClXW";

        uss.findOriginalUrl(shortcode);
        uss.findOriginalUrl(shortcode);

        Optional<UrlMapping> urlMapping = umr.findByShortCode(shortcode);

        assertTrue(urlMapping.isPresent());
        assertThat(urlMapping.get().getViewCount()).isEqualTo(2);
        assertNotNull(urlMapping.get().getViewedAt());

    }

}