package com.seungki.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.seungki.urlshortener.common.domain.UrlMapping;
import com.seungki.urlshortener.common.exception.UrlNotFoundException;
import com.seungki.urlshortener.common.repository.UrlMappingRepository;
import com.seungki.urlshortener.common.service.UrlShortenService;
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
class UrlShortenServiceTest {

    @Autowired
    private UrlMappingRepository umr;
    @Autowired
    private UrlShortenService uss;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void initData() {
        uss.shortenUrl("https://www.naver.com/");
        uss.shortenUrl("https://www.google.com");
    }

    @DisplayName("원본URL을 단축하면 관련 상세정보를 응답으로 받는다")
    @Test
    public void testShortenUrl() {

        String originalUrl = "https://www.youtube.com/";
        String shortcode = uss.shortenUrl(originalUrl).getShortcode();
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

        String originalShortcode = uss.shortenUrl(originalUrl).getShortcode();
        String saltUrlShortcode = uss.shortenUrl(originalUrl).getShortcode();

        assertThat(originalShortcode).isNotEqualTo(saltUrlShortcode);
    }

    @DisplayName("다음의 숏코드로 조회할 수 있다: qV4ClXW -> https://www.naver.com/")
    @Test
    public void testFindOriginalUrl() {

        String shortcode = "qV4ClXW";
        String originalUrl = "https://www.naver.com/";

        String findOriginalUrl = uss.findOriginalUrl(shortcode).getOriginalUrl();

        assertNotNull(findOriginalUrl);
        assertThat(findOriginalUrl).isEqualTo(originalUrl);

    }

    @DisplayName("존재하지 않는 숏코드로 원본 URL을 찾을시 UrlNotFoundException 발생")
    @Test
    public void testNullForNonExistingShortcode() {

        String nonExistingShortcode = "abcd123";

        assertThrows(UrlNotFoundException.class, () -> {
            String findUrl = uss.findOriginalUrl(nonExistingShortcode).getOriginalUrl();
        });

    }

    @DisplayName("원본URL을 성공적으로 조회시 조회수 증가, 조회 날짜 업데이트")
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