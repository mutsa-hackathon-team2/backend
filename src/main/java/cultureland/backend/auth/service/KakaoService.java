package cultureland.backend.auth.service;

import cultureland.backend.auth.dto.KakaoTokenResponse;
import cultureland.backend.auth.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final RestTemplate restTemplate;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.exchange(
                        KAKAO_TOKEN_URL,
                        HttpMethod.POST,
                        request,
                        KakaoTokenResponse.class
                );

        KakaoTokenResponse responseBody = response.getBody();

        if (responseBody == null || responseBody.getAccessToken() == null) {
            throw new IllegalStateException("카카오 액세스 토큰 발급에 실패했습니다.");
        }

        return responseBody.getAccessToken();

    }

    public KakaoUserResponse getUser(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserResponse> response =
                restTemplate.exchange(
                        KAKAO_USER_INFO_URL,
                        HttpMethod.GET,
                        request,
                        KakaoUserResponse.class
                );

        KakaoUserResponse responseBody = response.getBody();

        if (responseBody == null) {
            throw new IllegalStateException("카카오 사용자 정보 조회에 실패했습니다.");
        }

        return responseBody;
    }
}
