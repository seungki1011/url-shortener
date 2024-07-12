package com.seungki.urlshortener.web.service;

import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcode;
import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcodeWithSalt;

import com.seungki.urlshortener.web.domain.UrlMapping;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenService {

    private final UrlMappingRepository umr;
    private final EntityManager em;

    @Transactional
    public String shortenUrl(String originalUrl) {
        String shortcode = null;
        try {
            shortcode = saveUrlMapping(originalUrl);
            em.flush();
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.info("[Catch ConstraintViolationException | DataIntegrityViolationException] ", e);
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
