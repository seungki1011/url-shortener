package com.seungki.urlshortener.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ShortcodeDetailRequest {

    @NotEmpty(message = "공백을 허용하지 않습니다")
    private String shortcode;

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
}
