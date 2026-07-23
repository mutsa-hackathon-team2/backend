package cultureland.backend.auth.service;

import cultureland.backend.auth.dto.KakaoUserResponse;
import cultureland.backend.auth.dto.LoginResponse;
import cultureland.backend.member.entity.Member;
import cultureland.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoService kakaoService;
    private final MemberService memberService;

    @Transactional
    public LoginResponse loginWithKakao(String code){

        String accessToken = kakaoService.getAccessToken(code);
        KakaoUserResponse kakaoUser = kakaoService.getUser(accessToken);
        Member member = memberService.findOrCreateMember(
                kakaoUser.getId(),
                kakaoUser.getNickname()
        );

        return LoginResponse.from(member);
    }

    @Transactional(readOnly = true)
    public LoginResponse getLoginMember(Long memberId) {
        Member member = memberService.findMemberById(memberId);

        return LoginResponse.from(member);
    }

}
