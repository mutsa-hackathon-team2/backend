package cultureland.backend.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GeneralSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "COMMON200", "성공적으로 처리했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}