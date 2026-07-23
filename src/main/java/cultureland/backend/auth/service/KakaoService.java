package cultureland.backend.auth.service;

import cultureland.backend.auth.dto.KakaoTokenResponse;
import cultureland.backend.auth.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    public KakaoTokenResponse getAccessToken(String code) {

    }

    public KakaoUserResponse getUser(String accessToken) {
        
    }
}
