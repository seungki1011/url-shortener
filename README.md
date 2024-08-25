## 1. 개요

URL 단축이라는 것은 말 그대로 긴 URL을 단축해서 짧은 URL로 만드는 것을 말한다. 이런 URL 단축을 제공하는 가장 대표적인 서비스는 [**Bitly**](https://bitly.com/pages/products/url-shortener)이다.

짧아진 URL을 통해서 기존의 URL로 리다이렉트(redirect)된다.

Bitly를 사용해서 단축 URL을 얻어보면 다음과 같다.

- 원본 URL : [https://www.google.com/search?q=%EC%9E%90%EB%B0%94&oq=%EC%9E%90%EB%B0%94&gs_lcrp=EgZjaHJvbWUqDwgAEEUYOxiDARixAxiABDIPCAAQRRg7GIMBGLEDGIAEMgoIARAAGLEDGIAEMgoIAhAAGLEDGIAEMg0IAxAAGIMBGLEDGIAEMgYIBBBFGDwyBggFEEUYPDIGCAYQRRg8MgYIBxBFGEHSAQgyMjI4ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8](https://www.google.com/search?q=자바&oq=자바&gs_lcrp=EgZjaHJvbWUqDwgAEEUYOxiDARixAxiABDIPCAAQRRg7GIMBGLEDGIAEMgoIARAAGLEDGIAEMgoIAhAAGLEDGIAEMg0IAxAAGIMBGLEDGIAEMgYIBBBFGDwyBggFEEUYPDIGCAYQRRg8MgYIBxBFGEHSAQgyMjI4ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8)
- 단축된 URL : [https://bit.ly/3VzBS9q](https://bit.ly/3VzBS9q)

<br>

![bitly](https://seungki1011.github.io/post_images/2024-06-19-url-shortener-project-1/bitly.png)_bitly 사용_

<br>

짧아진 링크를 살펴보면 도메인인 https://bit.ly/ 뒤에 `3VzBS9q`라는 문자 조합이 붙어 있는 것을 확인할 수 있다. 이 문자 조합을 **숏키(shortkey) 또는 숏코드(shortcode)** 라고 부른다. **URL 단축의 핵심은 원본 URL을 유일한 숏코드로 매핑하는 로직을 구현하는 것**이다.

<br>

관련된 내용을 블로그에 정리하고 있다.

* [(Url Shortener - 1)URL 단축 서비스의 원리 파악하기](https://seungki1011.github.io/posts/url-shortener-project-1/)
* [(Url Shortener - 2)URL 단축 서비스의 요구 사항 및 설계](https://seungki1011.github.io/posts/url-shortener-project-2/)
* [(Url Shortener - 3)URL 단축 서비스 개발 시작, 발생한 문제들](https://seungki1011.github.io/posts/url-shortener-project-3/)
* [(Url Shortener - 4)URL 단축 서비스 API 개발](https://seungki1011.github.io/posts/url-shortener-project-3/)

---

## 2. 사용 기술

- Java `17`
- Spring Boot `3.3.1`
- JPA(Hibernate)
- Junit5
- Thymeleaf
- Lombok
- H2 `2.2.224`

---

## 3. 서버사이드 랜더링 결과

<br>

<p align="center">   <img src="https://seungki1011.github.io/post_images/2024-07-01-url-shortener-project-4/view1.png" alt="http" style="width: 80%;"> </p>

<p align="center">입력 폼</p>

<br>

<p align="center">   <img src="https://seungki1011.github.io/post_images/2024-07-01-url-shortener-project-4/view2.png" alt="http" style="width: 80%;"> </p>

<p align="center">단축 URL 상세 정보</p>

<br>

<p align="center">   <img src="https://seungki1011.github.io/post_images/2024-07-01-url-shortener-project-4/view3.png" alt="http" style="width: 80%;"> </p>

<p align="center">URL 검증</p>

<br>

---

## 4. API 명세

| 메서드 |          API 경로           | 출력 포맷 |              요청 파라미터               |           요청 본문            |         기능 설명         |
| :----: | :-------------------------: | :-------: | :--------------------------------------: | :----------------------------: | :-----------------------: |
| `POST` |      `api/v1/shorten`       |   JSON    |                                          | `url` : 단축할 원본 URL (JSON) |         URL 단축          |
| `GET`  | `api/v1/detail/{shortcode}` |   JSON    | `shortcode` : 단축 URL 숏코드 (`String`) |                                | 단축 URL의 상세 정보 조회 |

<br>

| Status |             Code              |                          설명                           |
| :----: | :---------------------------: | :-----------------------------------------------------: |
| `400`  |      `VALIDATION_ERROR`       |  입력값이 올바르지 않아서 검증을 통과하지 못하는 경우   |
| `404`  |     `SHORTCODE_NOT_FOUND`     |     해당 숏코드가 DB에 없는 경우 (상세 페이지 조회)     |
| `404`  |        `URL_NOT_FOUND`        | 해당 단축 URL의 숏코드가 DB에 없는 경우 (단축 URL 조회) |
| `500`  | `SHORTCODE_GENERATION_FAILED` |    중복된 숏코드에 대한 숏코드 재생성이 실패한 경우.    |

<br>

---

## 5. API 사용 예시

* `GET : api/v1/shorten`

| 메서드 |          API 경로           | 출력 포맷 |              요청 파라미터               |           요청 본문            |          기능 설명          |
| :----: | :-------------------------: | :-------: | :--------------------------------------: | :----------------------------: | :-------------------------: |
| `POST` |      `api/v1/shorten`       |   JSON    |                                          | `url` : 단축할 원본 URL (JSON) |          URL 단축           |

```bash
curl -X POST -H "Content-Type: application/json" -d '{"url":"https://www.youtube.com/watch?v=xvFZjo5PgG0"}' http://localhost:8080/api/v1/shorten
```

<br>

결과

```
{
  "shortcode": "apHgn4b",
  "originalUrl": "https://www.youtube.com/watch?v=xvFZjo5PgG0",
  "createdAt": "2024-07-05T10:25:00.701936",
  "viewedAt": null,
  "viewCount": 0
}
```

<br>

* `GET : api/v1/detail/{shortcode}`

| 메서드 |          API 경로           | 출력 포맷 |              요청 파라미터               | 요청 본문 |         기능 설명         |
| :----: | :-------------------------: | :-------: | :--------------------------------------: | :-------: | :-----------------------: |
| `GET`  | `api/v1/detail/{shortcode}` |   JSON    | `shortcode` : 단축 URL 숏코드 (`String`) |           | 단축 URL의 상세 정보 조회 |

```bash
curl "localhost:8080/api/v1/detail/apHgn4b"
```

<br>

결과

```json
{
  "shortcode": "apHgn4b",
  "originalUrl": "https://www.youtube.com/watch?v=xvFZjo5PgG0",
  "createdAt": "2024-07-05T10:25:00.701936",
  "viewedAt": null,
  "viewCount": 0
}
```

<br>

---

## Reference

1. [How to implemenet Tiny URL](https://www.youtube.com/watch?v=eCLqmPBIEYs&t=389s)
2. [Design a Shortener like Tiny URL](https://www.youtube.com/watch?v=zgIyzEEXfiA)
3. [문자를 다루는 인코딩 규칙](https://www.youtube.com/watch?v=6hvJr0-adtg)
4. [Base64 인코딩 원리](https://www.youtube.com/watch?v=A8tO4D1Gtc0&t=3s)
5. [https://www.freecodecamp.org/news/what-is-base64-encoding/](https://www.freecodecamp.org/news/what-is-base64-encoding/)
6. [https://en.wikipedia.org/wiki/Binary-to-text_encoding](https://en.wikipedia.org/wiki/Binary-to-text_encoding)







