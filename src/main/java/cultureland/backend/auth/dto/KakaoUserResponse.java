package cultureland.backend.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserResponse {

    private Long id;
    private Properties properties;

    @Getter
    @NoArgsConstructor
    public static class Properties {
        private String nickname;
    }

    public String getNickname() {
        return properties.getNickname();
    }
}
