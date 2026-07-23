package cultureland.backend.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private Long kakaoId;

    @Column(nullable = false)
    private String nickname;

    public static Member create(Long kakaoId, String nickname) {
        return Member.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .build();
    }
}
