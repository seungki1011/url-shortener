package com.seungki.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seungki.urlshortener.common.repository.UrlMappingRepository;
import com.seungki.urlshortener.common.service.UrlShortenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestDuplicateShortcode {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlShortenService uss;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @BeforeEach
    public void init() {
        urlMappingRepository.deleteAll();
    }


    @DisplayName("같은 URL을 이용한 POST 요청은 성공해야 한다")
    @Test
    public void test_post_shortenUrl() throws Exception {
        String originalUrl = "https://www.inflearn.com/";

        mockMvc.perform(post("/shorten")
                        .param("url", originalUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/detail/naA5WFV"));

        // 같은 URL 두 번째 요청
        mockMvc.perform(post("/shorten")
                        .param("url", originalUrl))
                .andExpect(status().is3xxRedirection());

    }

    @DisplayName("중복된 URL로 URL 단축을 수행하는 경우 숏코드가 서로 달라야 한다")
    //  @Transactional
    @Test
    public void test_duplicate_shortcode() {

        String originalUrl = "https://www.inflearn.com/";

        String originalShortcode = uss.shortenUrl(originalUrl).getShortcode();
        String saltUrlShortcode = uss.shortenUrl(originalUrl).getShortcode();

        assertThat(originalShortcode).isNotEqualTo(saltUrlShortcode);
    }

}
