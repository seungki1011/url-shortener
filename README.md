## 1. 간단 소개

URL 단축이라는 것은 말 그대로 긴 URL을 단축해서 짧은 URL로 만드는 것을 말한다. 이런 URL 단축을 제공하는 가장 대표적인 서비스는 [**Bitly**](https://bitly.com/pages/products/url-shortener)이다.

짧아진 URL을 통해서 기존의 URL로 리다이렉트(redirect)된다.

Bitly를 사용해서 단축 URL을 얻어보면 다음과 같다.

- 원본 URL : [https://www.google.com/search?q=%EC%9E%90%EB%B0%94&oq=%EC%9E%90%EB%B0%94&gs_lcrp=EgZjaHJvbWUqDwgAEEUYOxiDARixAxiABDIPCAAQRRg7GIMBGLEDGIAEMgoIARAAGLEDGIAEMgoIAhAAGLEDGIAEMg0IAxAAGIMBGLEDGIAEMgYIBBBFGDwyBggFEEUYPDIGCAYQRRg8MgYIBxBFGEHSAQgyMjI4ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8](https://www.google.com/search?q=자바&oq=자바&gs_lcrp=EgZjaHJvbWUqDwgAEEUYOxiDARixAxiABDIPCAAQRRg7GIMBGLEDGIAEMgoIARAAGLEDGIAEMgoIAhAAGLEDGIAEMg0IAxAAGIMBGLEDGIAEMgYIBBBFGDwyBggFEEUYPDIGCAYQRRg8MgYIBxBFGEHSAQgyMjI4ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8)
- 단축된 URL : [https://bit.ly/3VzBS9q](https://bit.ly/3VzBS9q)

<br>

![bitly](https://seungki1011.github.io/post_images/2024-06-19-url-shortener-project-1/bitly.png)_bitly 사용_

<br>

위에서 볼 수 있듯이 기존의 긴 URL을 짧아진 URL 링크를 통해서 접근할 수 있다.

짧아진 링크를 살펴보면 도메인인 `https://bit.ly/`뒤에 `3VzBS9q`라는 문자 조합이 붙어 있는 것을 확인할 수 있다. 이 문자 조합을 [슬러그(slug)](https://developer.mozilla.org/ko/docs/Glossary/Slug) 또는 숏코드(shortcode, shortkey)라고 부른다.

**URL 단축의 핵심이 원본 URL을 유일한(unique) 숏코드로 매핑하는 로직을 구현하는 것이다.**

<br>

관련된 내용을 블로그에 정리하고 있다.

* [(Url Shortener - 1)URL 단축 서비스의 원리 파악하기](https://seungki1011.github.io/posts/url-shortener-project-1/)
* [(Url Shortener - 2)URL 단축 서비스의 요구 사항 및 설계](https://seungki1011.github.io/posts/url-shortener-project-2/)
* [(Url Shortener - 3)URL 단축 서비스 개발 시작, 발생한 문제들](https://seungki1011.github.io/posts/url-shortener-project-3/)

<br>

---

## 2. 사용 기술

**Language**

- Java `17`



**Framework & Library**

- Spring Boot `3.3.1`
- Hibernate
- Junit5
- Thymeleaf
- Lombok



**Database**

- H2 `2.2.224` (테스트)

<br>

---

## 3. 요구 사항





## 4. API



