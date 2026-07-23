package cultureland.backend.event.dto;

import cultureland.backend.event.entity.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class EventSummaryResponse {

    private Long id;
    private String title;
    private String categoryName;
    private Integer price;
    private Long dDay;
    private String posterUrl;

    public static EventSummaryResponse from(Event event) {
        return EventSummaryResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .categoryName(event.getCategory().getName())
                .price(event.getPrice())
                .dDay(calculateDDay(event.getStartDate().toLocalDate()))
                .posterUrl(event.getPosterUrl())
                .build();
    }

    private static Long calculateDDay(LocalDate startDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), startDate);
    }

}
