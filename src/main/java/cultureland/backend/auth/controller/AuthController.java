package cultureland.backend.auth.controller;

import cultureland.backend.auth.dto.LoginResponse;
import cultureland.backend.auth.service.AuthService;
import cultureland.backend.global.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${frontend.redirect-uri}")
    private String frontendRedirectUri;

    @GetMapping("/kakao/callback")
    public String kakaoCallback(
            @RequestParam String code,
            HttpServletRequest request
    ) {
        LoginResponse response =
                authService.loginWithKakao(code);

        HttpSession session = request.getSession();

        request.changeSessionId();

        session.setAttribute(
                SessionConst.LOGIN_MEMBER_ID,
                response.getMemberId()
        );

        return "redirect:" + frontendRedirectUri;
    }
}
