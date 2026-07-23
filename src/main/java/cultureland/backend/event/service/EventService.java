package cultureland.backend.event.service;

import cultureland.backend.category.entity.Category;
import cultureland.backend.category.repository.CategoryRepository;
import cultureland.backend.event.dto.EventCreateRequest;
import cultureland.backend.event.dto.EventDetailResponse;
import cultureland.backend.event.dto.EventSummaryResponse;
import cultureland.backend.event.entity.Event;
import cultureland.backend.event.exception.EventErrorCode;
import cultureland.backend.event.repository.EventRepository;
import cultureland.backend.global.apiPayload.exception.GeneralException;
import cultureland.backend.global.s3.S3Service;
import cultureland.backend.member.entity.Member;
import cultureland.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    public List<EventSummaryResponse> getEvents(Long categoryId) {
        List<Event> events = (categoryId == null)
                ? eventRepository.findAll()
                : eventRepository.findByCategoryId(categoryId);

        return events.stream()
                .map(EventSummaryResponse::from)
                .toList();
    }

    public EventDetailResponse getEventDetail(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new GeneralException(EventErrorCode.EVENT_NOT_FOUND));

        return EventDetailResponse.from(event);
    }

    @Transactional
    public EventDetailResponse createEvent(
            Long memberId,
            EventCreateRequest request,
            MultipartFile poster
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new GeneralException(EventErrorCode.MEMBER_NOT_FOUND)
                );

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new GeneralException(EventErrorCode.CATEGORY_NOT_FOUND)
                );

        String posterUrl = s3Service.uploadPoster(poster);

        Event event = Event.create(
                member,
                category,
                request.getTitle(),
                request.getDescription(),
                request.getLocation(),
                request.getPrice(),
                request.getStartDate(),
                request.getEndDate(),
                posterUrl
        );

        return EventDetailResponse.from(eventRepository.save(event));
    }

}
