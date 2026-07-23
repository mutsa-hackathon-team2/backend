package cultureland.backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokenResponse {
    // 카카오에서 발급하는 access token
    @JsonProperty("access_token")
    String accessToken;
}
