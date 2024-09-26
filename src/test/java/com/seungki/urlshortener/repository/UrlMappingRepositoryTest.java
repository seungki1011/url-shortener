package com.seungki.urlshortener.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.seungki.urlshortener.common.domain.UrlMapping;
import com.seungki.urlshortener.common.repository.UrlMappingRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UrlMappingRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UrlMappingRepository umr;

    @DisplayName("엔티티를 저장하면 조회할 수 있다")
    @Test
    public void testSaveUrlMapping() {
        UrlMapping urlMapping = new UrlMapping("abc123", "https://www.naver.com", LocalDateTime.now());

        umr.save(urlMapping);

        Optional<UrlMapping> foundUrl = umr.findByShortCode("abc123");

        assertThat(foundUrl.isPresent()).isTrue();
        assertThat(foundUrl.get().getOriginalUrl()).isEqualTo(urlMapping.getOriginalUrl());

    }

    @DisplayName("숏코드 원본URL을 조회할 수 있어야 한다")
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