package com.seungki.urlshortener.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.seungki.urlshortener.common.service.UrlShortenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class TestUrlValidation {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlShortenService uss;

    @DisplayName("앞에 프로토콜을 붙이지 않은 URL을 사용했을때 검증을 통과하지 못하고 에러가 발생한다")
    @Test
    public void test_no_protocol() throws Exception {

        String invalidUrl = "www.google.com";

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/shorten")
                .param("url", invalidUrl));

        result.andExpect(status().isOk())
                .andExpect(view().name("shortener_form"))
                .andExpect(model().attributeHasFieldErrors("urlShortenRequest", "url"));

    }

    @DisplayName("\"[a-zA-Z0-9-._~:/?#@!$&'()*+,;=%]+\"의 패턴을 만족하지 못하는 URL을 사용하면 검증을 통과하지 못하고 에러가 발생한다")
    @Test
    public void test_unsafe_url() throws Exception {

        String invalidUrl = "한글이들어간Url.com";

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/shorten")
                .param("url", invalidUrl));

        result.andExpect(status().isOk())
                .andExpect(view().name("shortener_form"))
                .andExpect(model().attributeHasFieldErrors("urlShortenRequest", "url"));

    }

}
