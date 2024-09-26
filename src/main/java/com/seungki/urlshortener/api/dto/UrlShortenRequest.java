package com.seungki.urlshortener.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UrlShortenRequest {
    @NotEmpty(message = "공백을 허용하지 않습니다")
    @Pattern.List({
            @Pattern(
                    regexp = "^(http://|https://).*",
                    message = "URL은 http:// 또는 https://로 시작해야 합니다"
            ),
            @Pattern(
                    regexp = "[a-zA-Z0-9-._~:/?#@!$&'()*+,;=%]+",
                    message = "URL은 영문자, 숫자 그리고 특수 문자(._~:/?#@!$&'()*+,;=%)만 허용합니다"
            )
    })
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }
}