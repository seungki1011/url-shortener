package com.seungki.urlshortener.common.service;

import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcode;
import static com.seungki.urlshortener.util.ShortCodeUtil.generateShortcodeWithSalt;

import com.seungki.urlshortener.common.domain.UrlMapping;
import com.seungki.urlshortener.common.exception.ShortcodeGenerationException;
import com.seungki.urlshortener.common.exception.ShortcodeNotFoundException;
import com.seungki.urlshortener.common.exception.UrlNotFoundException;
import com.seungki.urlshortener.common.repository.UrlMappingRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShortenService {

    private static final int MAX_RETRIES = 10;
    private final UrlMappingRepository umr;
    private final EntityManager em;

    /**
     * 기존 코드: UrlMappingRepository의 save()에 @Transactional(propagation = Propagation.REQUIRES_NEW)가 필요
     */
    //    @Transactional
    //    public UrlMapping shortenUrl(String originalUrl) {
    //        String shortcode;
    //
    //        for (int i = 0; i < MAX_RETRIES; i++) {
    //            try {
    //                shortcode = generateShortcode(originalUrl);
    //                if (i != 0) {
    //                    shortcode = generateShortcodeWithSalt(originalUrl);
    //                    log.info("[generateShortcodeWithSalt] shortcode = {}", shortcode);
    //                }
    //                UrlMapping urlMapping = new UrlMapping(shortcode, originalUrl, LocalDateTime.now());
    //                umr.save(urlMapping);
    //                em.flush();
    //                return urlMapping;
    //            } catch (DataIntegrityViolationException | ConstraintViolationException e) {
    //                log.warn("[Shortcode Collision] Retrying... attempt {}", i + 1);
    //            }
    //        }
    //
    //        throw new ShortcodeGenerationException(
    //                "Unable to generate unique shortcode after " + MAX_RETRIES + " attempts");
    //    }

    /**
     * ShortcodeCollisionException를 정의하고 서비스 계층에서 잡아서 처리하는 경우
     */
    //    @Transactional
    //    public UrlMapping shortenUrl(String originalUrl) {
    //        String shortcode;
    //
    //        for (int i = 0; i < MAX_RETRIES; i++) {
    //            try {
    //                shortcode = generateShortcode(originalUrl);
    //                if (i != 0) {
    //                    shortcode = generateShortcodeWithSalt(originalUrl);
    //                    log.info("[generateShortcodeWithSalt] shortcode = {}", shortcode);
    //                }
    //                UrlMapping urlMapping = new UrlMapping(shortcode, originalUrl, LocalDateTime.now());
    //
    //                // 숏코드 중복 검사 미리 수행
    //                if (umr.findByShortCode(shortcode).isPresent()) {
    //                    throw new ShortcodeCollisionException("Shortcode Already Exists: " + shortcode);
    //                }
    //                umr.save(urlMapping);
    //                return urlMapping;
    //            } catch (ShortcodeCollisionException e) {
    //                log.warn("[Shortcode Collision] Retrying... attempt {}", i + 1);
    //            } catch (DataIntegrityViolationException | ConstraintViolationException e) {
    //                log.warn("[Database Constraint] Retrying... attempt {}", i + 1);
    //            }
    //        }
    //        throw new ShortcodeGenerationException(
    //                "Unable to generate unique shortcode after " + MAX_RETRIES + " attempts");
    //    }

    /**
     * 기존 코드에서 UrlMapping을 저장하는 로직을 새로운 트랜잭션으로 분리
     * 마찬가지로 UrlMappingRepository의 save()에 @Transactional(propagation = Propagation.REQUIRES_NEW) 필요
     */
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
                // 이 부분을 REQUIRES_NEW 트랜잭션으로 분리
                return saveUrlMappingWithNewTransaction(shortcode, originalUrl);

            } catch (DataIntegrityViolationException | ConstraintViolationException e) {
                log.warn("[Shortcode Collision] Retrying... attempt {}", i + 1);
            }
        }

        throw new ShortcodeGenerationException(
                "Unable to generate unique shortcode after " + MAX_RETRIES + " attempts");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UrlMapping saveUrlMappingWithNewTransaction(String shortcode, String originalUrl) {
        UrlMapping urlMapping = new UrlMapping(shortcode, originalUrl, LocalDateTime.now());
        umr.save(urlMapping);
        return urlMapping;
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
