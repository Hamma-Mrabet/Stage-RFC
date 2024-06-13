package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findEventsByNameEvent(String name);

    List<Event> findEventsByUserIdUser(long id);




    @Query("SELECT e FROM Event e WHERE e.typeEvent IN " +
            "(SELECT t.event.typeEvent FROM Ticket t WHERE t.user.idUser = :userId " +
            "GROUP BY t.event.typeEvent ORDER BY SUM(t.nbts) DESC)")
    List<Event> findEventsByPreferences(@Param("userId") Long userId);











    // JPQL
    @Query("SELECT e FROM Event e WHERE e.nameEvent =:name")
    List<Event> retrieveEventsByNameEvent(@Param("name") String name);

    @Query("SELECT e FROM Event e ORDER BY e.price ASC")
    List<Event> findAllByOrderByPriceAsc();

    @Query("SELECT e FROM Event e ORDER BY e.nbt ASC")
    List<Event> findAllEventsOrderedByNbt();









}
