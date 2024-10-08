package com.seungki.urlshortener.common.repository;

import com.seungki.urlshortener.common.domain.UrlMapping;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UrlMappingRepository {

    final private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM UrlMapping").executeUpdate();
    }

}
