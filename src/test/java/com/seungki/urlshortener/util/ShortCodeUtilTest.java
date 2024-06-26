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
}