package com.seungki.urlshortener.web.service;

import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcode;
import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcodeWithSalt;

import com.seungki.urlshortener.web.domain.UrlMapping;
import com.seungki.urlshortener.web.exception.ShortcodeGenerationException;
import com.seungki.urlshortener.web.exception.ShortcodeNotFoundException;
import com.seungki.urlshortener.web.exception.UrlNotFoundException;
import com.seungki.urlshortener.web.repository.UrlMappingRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShortenService {

    private final int MAX_RETRIES = 10;
    private final UrlMappingRepository umr;
    private final EntityManager em;

    @Transactional
    public UrlMapping shortenUrl(String originalUrl) {
        String shortcode;

        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                shortcode = generateShortcode(originalUrl);
                if (i != 0) {
                    shortcode = generateShortcodeWithSalt(originalUrl);
                    log.info("[generateShortcodeWithSalt] shortcode = {}", shortcode);
                }
                UrlMapping urlMapping = new UrlMapping(shortcode, originalUrl, LocalDateTime.now());
                umr.save(urlMapping);
                em.flush();
                return urlMapping;
            } catch (DataIntegrityViolationException | ConstraintViolationException e) {
                log.warn("[Shortcode Collision] Retrying... attempt {}", i + 1);
            }
        }

        throw new ShortcodeGenerationException(
                "Unable to generate unique shortcode after " + MAX_RETRIES + " attempts");
    }

    @Transactional
    public UrlMapping findOriginalUrl(String shortcode) {
        return umr.findByShortCode(shortcode)
                .map(urlMapping -> {
                    urlMapping.incrementViewCount();
                    return urlMapping;
                })
                .orElseThrow(() -> new UrlNotFoundException(shortcode));
    }

    @Transactional(readOnly = true)
    public UrlMapping findMatchingUrl(String shortcode) {
        return umr.findByShortCode(shortcode)
                .orElseThrow(() -> new ShortcodeNotFoundException(shortcode));
    }

}
