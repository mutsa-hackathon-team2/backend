package cultureland.backend.auth.controller;

import cultureland.backend.auth.dto.LoginResponse;
import cultureland.backend.auth.service.AuthService;
import cultureland.backend.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/callback")
    public ApiResponse<LoginResponse> kakaoCallback(
            @RequestParam String code
    ) {
        return ApiResponse.onSuccess(
                authService.loginWithKakao(code)
        );
    }
}
