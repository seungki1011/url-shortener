package com.seungki.urlshortener.controller;

import com.seungki.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService uss;

    @GetMapping({"/", "/shorten"})
    public String shortenerForm() {
        return "shortener_form";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("url") String originalUrl) {
        String shortcode = uss.shortenUrl(originalUrl);
        return "redirect:/detail/" + shortcode;
    }

    @GetMapping("/detail/{shortcode}")
    public String shortenerDetail(@PathVariable String shortcode, Model model) {
        model.addAttribute("shortcode", shortcode);
        return "shortener_detail";
    }

    @GetMapping("/{shortcode}")
    public String redirectToOriginalUrl(@PathVariable String shortcode) {
        String originalUrl = uss.findOriginalUrl(shortcode);
        if (originalUrl != null) {
            return "redirect:" + originalUrl;
        } else {
            return "error";
        }
    }
}
