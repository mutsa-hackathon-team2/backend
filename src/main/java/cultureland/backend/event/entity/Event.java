package cultureland.backend.event.entity;

import cultureland.backend.category.entity.Category;
import cultureland.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public static Event create(Member memberId, Category category, String title, String description, String location, int price, LocalDateTime startDate, LocalDateTime endDate){
        return Event.builder()
                .memberId(memberId)
                .category(category)
                .title(title)
                .description(description)
                .location(location)
                .price(price)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
