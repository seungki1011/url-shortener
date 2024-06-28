package com.seungki.urlshortener.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ShortCodeUtil {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();
    private static final int SHORTCODE_LENGTH = 7;

    /**
     * 원본 URL을 입력으로 받고 해시 함수를 통해서 해시값으로 변환한다.
     * 해시값은 Base62 인코더를 통해 Base62로 인코딩 된다.
     * 해당 Base62 문자열을 앞 7자리를 truncate해서 숏코드(숏키)로 사용한다.
     */
    public static String generateShortcode(String originalUrl) {
        byte[] hash = hashFunction(originalUrl);
        String base62Encoded = Base62.encode(hash);

        return base62Encoded.substring(0, SHORTCODE_LENGTH);
    }

    /**
     * 서비스 계층의 shortenUrl()에서 ConstraintViolationException 핸들링을 위해 사용한다.
     * 서비스 계층의 shortenUrl()에서 중복된 숏코드가 생성되는 경우 ConstraintViolationException이 발생한다.
     * 원본 URL을 입력으로 받고 솔트(salt)값을 추가해서 숏코드를 생성한다.
     */
    public static String generateShortcodeWithSalt(String originalUrl) {
        String urlWithSalt = originalUrl + generateRandomString(10);
        byte[] hash = hashFunction(urlWithSalt);
        String base62Encoded = Base62.encode(hash);

        return base62Encoded.substring(0, SHORTCODE_LENGTH);
    }

    /**
     * Base62의 ASCII 세트 내의 문자를 가지고 명시한 길이 만큼의 랜덤 문자열을 생성한다.
     */
    private static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            randomString.append(ALPHABET.charAt(random.nextInt(BASE)));
        }
        return randomString.toString();
    }

    /**
     * 원본 URL을 입력으로 받아서 SHA-256 방식으로 해시값을 생성한다.
     */
    private static byte[] hashFunction(String originalUrl) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(originalUrl.getBytes());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
