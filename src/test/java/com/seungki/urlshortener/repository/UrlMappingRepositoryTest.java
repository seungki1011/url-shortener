package com.seungki.urlshortener.repository;

import com.seungki.urlshortener.domain.UrlMapping;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UrlMappingRepositoryTest {

    @Autowired EntityManager em;
    @Autowired UrlMappingRepository umr;

    @DisplayName("UrlMapping 엔티티를 저장하면 조회가 가능해야 한다.")
    @Test
    public void testSaveUrlMapping() {
        UrlMapping urlMapping = new UrlMapping("abc123", "https://www.naver.com", LocalDateTime.now());

        umr.save(urlMapping);

        Optional<UrlMapping> foundUrl = umr.findByShortCode("abc123");

        assertThat(foundUrl.isPresent()).isTrue();
        assertThat(foundUrl.get().getOriginalUrl()).isEqualTo(urlMapping.getOriginalUrl());

    }

    @DisplayName("숏코드로 검색해서 원본 URL을 찾을 수 있어야한다.")
    @Test
    public void testFindByShortCode() {

        UrlMapping urlMapping = new UrlMapping("abc123", "https://www.naver.com", LocalDateTime.now());
        em.persist(urlMapping);
        em.flush();

        Optional<UrlMapping> foundUrl = umr.findByShortCode("abc123");

        assertThat(foundUrl.isPresent()).isTrue();
        assertThat(foundUrl.get().getOriginalUrl()).isEqualTo(urlMapping.getOriginalUrl());

    }
}