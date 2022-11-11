package ai.codemap.codemap;

import ai.codemap.codemap.dto.KakaoTokenResponse;
import ai.codemap.codemap.form.PaizaPostResponseForm;
import ai.codemap.codemap.loginDto.KakaoUser;


import lombok.Data;
import org.apache.http.HttpHeaders;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class KakaoOAuth2 {
    final String baseURL = "https://api.codemap.ai";
    //final String baseURL = "http://localhost:8081";
    final WebClient client = WebClient.builder()
            .baseUrl("https://kauth.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public KakaoUser getUserInfo(String code, String endPoint) {
        String token = accessToken(code, endPoint);
        KakaoUser kakaoUser = getUserByKakaoToken(token);

        return kakaoUser;
    }

    private String accessToken(String code, String endPoint) {

        Object response = client.post().
                uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", "f796398f8dc1c3d64a37a9e053a9be9b")
                        .queryParam("redirect_uri", baseURL+"/users/kakao/"+endPoint)
                        .queryParam("code", code)
                        .build())
                .retrieve().bodyToMono(Object.class).block();
        Map<String, Object> map = (Map<String, Object>) response;
        System.out.println(map.get("access_token"));

        return (String) map.get("access_token");
    }

    private KakaoUser getUserByKakaoToken(String token) {
        WebClient client2 = WebClient.builder()
                    .baseUrl("https://kapi.kakao.com")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

        Object response = client2.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                .header("Authorization", "Bearer " + token)
                .retrieve().bodyToMono(Object.class).block();

        System.out.println(response);

        Map<String, Object> res = (Map<String, Object>) response;
        Map<String, Object> account = (Map<String, Object>) res.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");


        KakaoUser kakaoUser = KakaoUser.builder()
                .nickname((String) profile.get("nickname"))
                .image((String) profile.get("profile_image_url"))
                .email((String) account.get("email"))
                .id((Long) res.get("id"))
                .build();

        System.out.println(kakaoUser);
        return kakaoUser;
    }

}

