package com.seungki.urlshortener.repository;

import com.seungki.urlshortener.domain.UrlMapping;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UrlMappingRepository {

    final private EntityManager em;

    public void save(UrlMapping urlMapping) {
        em.persist(urlMapping);
    }

    public Optional<UrlMapping> findByShortCode(String shortcode) {
        String query = "SELECT u FROM UrlMapping u WHERE u.shortcode = :shortcode";
        UrlMapping urlMapping = em.createQuery(query, UrlMapping.class)
                .setParameter("shortcode", shortcode)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(urlMapping);
    }

}
