package cultureland.backend.event.service;

import cultureland.backend.event.dto.EventDetailResponse;
import cultureland.backend.event.dto.EventSummaryResponse;
import cultureland.backend.event.entity.Event;
import cultureland.backend.event.exception.EventErrorCode;
import cultureland.backend.event.repository.EventRepository;
import cultureland.backend.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

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

}
