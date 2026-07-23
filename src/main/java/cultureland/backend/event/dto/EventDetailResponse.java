package cultureland.backend.event.dto;

import cultureland.backend.event.entity.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventDetailResponse {

    private Long id;
    private String title;
    private String description;
    private Integer price;
    private String location;
    private String categoryName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String posterUrl;

    public static EventDetailResponse from(Event event) {
        return EventDetailResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .price(event.getPrice())
                .location(event.getLocation())
                .categoryName(event.getCategory().getName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .posterUrl(event.getPosterUrl())
                .build();
    }

}
