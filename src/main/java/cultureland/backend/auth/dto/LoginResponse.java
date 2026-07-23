package cultureland.backend.auth.dto;

import cultureland.backend.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private Long memberId;
    private String nickname;

    public static LoginResponse from(Member member){
        return LoginResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
