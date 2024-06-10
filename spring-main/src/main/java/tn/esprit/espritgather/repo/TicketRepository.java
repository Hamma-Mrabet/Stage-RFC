package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Ticket;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TicketRepository  extends JpaRepository<Ticket,Long> {

    List<Ticket> findTicketsByEventIdEvent(Long eventId);

    List<Ticket> findTicketsByEventIdEventAndUserIdUser(Long eventId,Long userId);



    @Query("SELECT t.event.typeEvent, SUM(t.nbts) AS totalNbts " +
            "FROM Ticket t " +
            "WHERE t.user.idUser = :userId " +
            "GROUP BY t.event.typeEvent " +
            "ORDER BY totalNbts DESC")
    List<Object[]> findTotalNbtsByEventType(@Param("userId") Long userId);

    @Query("SELECT t.event, SUM(t.nbts) AS totalNbts " +
            "FROM Ticket t " +
            "WHERE t.user.idUser = :userId " +
            "GROUP BY t.event")
    List<Object[]> findTotalNbtsByEventType1(@Param("userId") Long userId);




    @Query("SELECT t.dateAchat, SUM(t.nbts) FROM Ticket t GROUP BY t.dateAchat")
    List<Object[]> findTotalTicketsByDateAchat();

    @Query("SELECT t.typePay, SUM(t.nbts) FROM Ticket t GROUP BY t.typePay")
    List<Object[]> findTotalTicketsByTypeAchat();

    @Query("SELECT t.event.nameEvent, SUM(e.price * t.nbts) FROM Ticket t JOIN t.event e GROUP BY t.event.idEvent")
    List<Object[]> findTotalPricesByEvent();


    @Query("SELECT t.event.nameEvent, SUM(e.price * t.nbts) FROM Ticket t JOIN t.event e WHERE e.user.idUser = :userId GROUP BY t.event.idEvent")
    List<Object[]> findTotalPricesByEventWithUserId(@Param("userId") Long userId);






}
