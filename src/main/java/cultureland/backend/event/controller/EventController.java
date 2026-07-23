package cultureland.backend.event.controller;

import cultureland.backend.event.dto.EventCreateRequest;
import cultureland.backend.event.dto.EventDetailResponse;
import cultureland.backend.event.dto.EventSummaryResponse;
import cultureland.backend.event.service.EventService;
import cultureland.backend.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ApiResponse<List<EventSummaryResponse>> getEvents(
            @RequestParam(required = false) Long categoryId
    ) {
        return ApiResponse.onSuccess("행사 목록 조회에 성공했습니다.", eventService.getEvents(categoryId));
    }

    @GetMapping("/{eventId}")
    public ApiResponse<EventDetailResponse> getEventDetail(@PathVariable Long eventId) {
        return ApiResponse.onSuccess("행사 상세 조회에 성공했습니다.", eventService.getEventDetail(eventId));
    }

    @PostMapping
    public ApiResponse<EventDetailResponse> createEvent(
            @RequestParam Long memberId, // TODO: 로그인 구현 후 인증 정보에서 추출하도록 변경
            @RequestBody @Valid EventCreateRequest request
    ) {
        return ApiResponse.onSuccess("행사 등록에 성공했습니다.", eventService.createEvent(memberId, request));
    }
}
