package cultureland.backend.event.repository;

import cultureland.backend.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategoryId(Long categoryId);

}
