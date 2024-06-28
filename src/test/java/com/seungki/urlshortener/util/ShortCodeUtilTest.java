package com.seungki.urlshortener.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShortCodeUtilTest {

    @DisplayName("https://www.naver.com/ -> qV4ClXW")
    @Test
    public void testGenerateShortCode() {
        String originalUrl = "https://www.naver.com/";
        String shortcode = ShortCodeUtil.generateShortcode(originalUrl);

        assertThat(shortcode).isEqualTo("qV4ClXW");
    }

    @DisplayName("[salt 포함] https://www.naver.com/ -> 랜덤 Base62 값")
    @Test
    public void testGenerateShortCodeWithSalt() {
        String originalUrl = "https://www.naver.com/";
        String shortcode = ShortCodeUtil.generateShortcodeWithSalt(originalUrl);

        assertThat(shortcode).isNotEqualTo("qV4ClXW");
    }
}