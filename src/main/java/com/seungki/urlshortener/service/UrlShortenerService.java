package com.seungki.urlshortener.service;

import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcode;
import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcodeWithSalt;

import com.seungki.urlshortener.domain.UrlMapping;
import com.seungki.urlshortener.repository.UrlMappingRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerService {

    private final UrlMappingRepository umr;
    private final EntityManager em;

    @Transactional
    public String shortenUrl(String originalUrl) {
        String shortcode = null;
        try {
            shortcode = saveUrlMapping(originalUrl);
            em.flush();
            log.info("[No Duplication] shortcode = {}", shortcode);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.info("[Exception!] ", e);
            shortcode = handleShortcodeDuplication(originalUrl);
        }
        return shortcode;
    }
    
    public String saveUrlMapping(String originalUrl) {
        String shortcode = generateShortcode(originalUrl);
        UrlMapping urlMapping = new UrlMapping(shortcode, originalUrl, LocalDateTime.now());
        umr.save(urlMapping);
        return shortcode;
    }

    public String handleShortcodeDuplication(String originalUrl) {
        String newShortcode = generateShortcodeWithSalt(originalUrl);
        log.info("[Shortcode Duplication] Salted shortcode = {}", newShortcode);
        UrlMapping urlMapping = new UrlMapping(newShortcode, originalUrl, LocalDateTime.now());
        umr.save(urlMapping);
        return newShortcode;
    }

    @Transactional
    public String findOriginalUrl(String shortcode) {
        return umr.findByShortCode(shortcode)
                .map(urlMapping -> {
                    urlMapping.incrementViewCount();
                    return urlMapping.getOriginalUrl();
                })
                .orElse(null);
    }

}
