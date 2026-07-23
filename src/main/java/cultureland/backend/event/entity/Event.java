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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
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

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    public static Event create(
            Member member,
            Category category,
            String title,
            String description,
            String location,
            int price,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String posterUrl
    ) {
        return Event.builder()
                .member(member)
                .category(category)
                .title(title)
                .description(description)
                .location(location)
                .price(price)
                .startDate(startDate)
                .endDate(endDate)
                .posterUrl(posterUrl)
                .build();
    }

}
