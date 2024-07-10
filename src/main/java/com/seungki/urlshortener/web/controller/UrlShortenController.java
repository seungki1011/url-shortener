package com.seungki.urlshortener.web.controller;

import com.seungki.urlshortener.web.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UrlShortenController {

    private final UrlShortenService uss;

    @GetMapping({"/", "/shorten"})
    public String shortenerForm(Model model) {
        model.addAttribute("urlShortenRequest", new UrlShortenRequest());
        return "shortener_form";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@ModelAttribute("urlShortenRequest") @Validated UrlShortenRequest usr,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "shortener_form";
        }

        String shortcode = uss.shortenUrl(usr.getUrl());
        redirectAttributes.addAttribute("shortcode", shortcode);
        return "redirect:/detail/{shortcode}";
    }

    @GetMapping("/detail/{shortcode}")
    public String shortenerDetail(@PathVariable String shortcode, Model model) {
        model.addAttribute("shortcode", shortcode);
        return "shortener_detail";
    }

    @GetMapping("/{shortcode}")
    public String redirectToOriginalUrl(@PathVariable String shortcode, RedirectAttributes redirectAttributes) {
        String originalUrl = uss.findOriginalUrl(shortcode);

        if (originalUrl != null) {
            redirectAttributes.addAttribute("originalUrl", originalUrl);
            return "redirect:{originalUrl}";
        } else {
            return "error";
        }
    }
}
