package cultureland.backend.auth.controller;

import cultureland.backend.auth.dto.LoginResponse;
import cultureland.backend.auth.service.AuthService;
import cultureland.backend.global.apiPayload.ApiResponse;
import cultureland.backend.global.session.SessionConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "Auth", description = "카카오 로그인 인증 API")
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${frontend.redirect-uri}")
    private String frontendRedirectUri;

    @Operation(
            summary = "카카오 로그인 콜백",
            description = "카카오 인가 코드를 받아 로그인/회원가입을 처리하고, 세션 저장 후 프론트엔드로 리다이렉트합니다."
    )
    @GetMapping("/kakao/callback")
    public String kakaoCallback(
            @Parameter(description = "카카오 인가 코드") @RequestParam String code,
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

    @Operation(summary = "로그인 상태 확인")
    @ResponseBody
    @GetMapping("/session")
    public ApiResponse<Boolean> checkSession(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute(
                        SessionConst.LOGIN_MEMBER_ID
                ) != null;

        return ApiResponse.onSuccess(
                "로그인 상태를 확인했습니다.",
                loggedIn
        );
    }
}
