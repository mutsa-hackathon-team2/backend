package cultureland.backend.member.service;

import cultureland.backend.member.entity.Member;
import cultureland.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findOrCreateMember(Long kakaoId, String nickname) {
        return memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() ->
                        memberRepository.save(
                                Member.create(kakaoId, nickname)
                        )
                );
    }
}
