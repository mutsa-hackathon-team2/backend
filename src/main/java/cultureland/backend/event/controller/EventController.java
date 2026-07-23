package cultureland.backend.event.controller;

import cultureland.backend.event.dto.EventCreateRequest;
import cultureland.backend.event.dto.EventDetailResponse;
import cultureland.backend.event.dto.EventSummaryResponse;
import cultureland.backend.event.service.EventService;
import cultureland.backend.global.apiPayload.ApiResponse;
import cultureland.backend.global.apiPayload.code.GeneralErrorCode;
import cultureland.backend.global.apiPayload.exception.GeneralException;
import cultureland.backend.global.session.SessionConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Event", description = "행사 조회/등록 API")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "행사 목록 조회", description = "categoryId가 없으면 전체 행사를 조회합니다.")
    @GetMapping
    public ApiResponse<List<EventSummaryResponse>> getEvents(
            @Parameter(description = "카테고리 ID (선택)") @RequestParam(required = false) Long categoryId
    ) {
        return ApiResponse.onSuccess("행사 목록 조회에 성공했습니다.", eventService.getEvents(categoryId));
    }

    @Operation(summary = "행사 상세 조회")
    @GetMapping("/{eventId}")
    public ApiResponse<EventDetailResponse> getEventDetail(@PathVariable Long eventId) {
        return ApiResponse.onSuccess("행사 상세 조회에 성공했습니다.", eventService.getEventDetail(eventId));
    }

    @Operation(summary = "행사 등록", description = "로그인한 회원이 포스터와 행사를 등록합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<EventDetailResponse> createEvent(
            @RequestPart("request") @Valid EventCreateRequest request,
            @RequestPart("poster") MultipartFile poster,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }

        Long memberId = (Long) session.getAttribute(
                SessionConst.LOGIN_MEMBER_ID
        );

        if (memberId == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }

        return ApiResponse.onSuccess(
                "행사 등록에 성공했습니다.",
                eventService.createEvent(memberId, request, poster)
        );
    }
}
