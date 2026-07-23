package cultureland.backend.event.controller;

import cultureland.backend.event.dto.EventDetailResponse;
import cultureland.backend.event.dto.EventSummaryResponse;
import cultureland.backend.event.service.EventService;
import cultureland.backend.global.apiPayload.ApiResponse;
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

}
