package tn.esprit.espritgather.service;

import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Ticket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITicketService {
    public List<Ticket> retrieveAllTickets();
    public Ticket retrieveTicket(Long idTicket);
    public Ticket addTicket(Ticket c);
    public void removeTicket(Long idTicket);
    public Ticket modifyTicket(Ticket ticket);
    List<Ticket> retrieveTicketsByEvent(Long eventId);

    Map<String, Long> getTotalNbtsByEventType(Long userId);


    List<Ticket> retrieveTicketsByEventAndUser(Long eventId,Long userId);

    Map<String, Long> getTotalTicketsByDateAchat();

    Map<String, Long> findTotalTicketsByTypeAchat();

    Map<String, Double> findTotalPricesByEvent();

    Map<String, Double> findTotalPricesByEventUser(Long userId);



}