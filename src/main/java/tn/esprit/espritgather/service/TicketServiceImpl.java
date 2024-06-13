package tn.esprit.espritgather.service;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.repo.TicketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements ITicketService {
    TicketRepository ticketRepository;

    public List<Ticket> retrieveAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket retrieveTicket(Long ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public Ticket addTicket(Ticket c) {
        return ticketRepository.save(c);
    }

    public void removeTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    public Ticket modifyTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> retrieveTicketsByEvent(Long eventId) {
        return ticketRepository.findTicketsByEventIdEvent(eventId);
    }





    public List<Ticket> retrieveTicketsByEventAndUser(Long eventId, Long userId) {
        return ticketRepository.findTicketsByEventIdEventAndUserIdUser(eventId,userId);
    }

    public Map<String, Long> getTotalTicketsByDateAchat() {
        List<Object[]> results = ticketRepository.findTotalTicketsByDateAchat();
        Map<String, Long> totalTicketsByDate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] row : results) {
            Date dateAchat = (Date) row[0];
            Long nbts = (Long) row[1];
            String dateKey = dateFormat.format(dateAchat);
            totalTicketsByDate.merge(dateKey, nbts, Long::sum);
        }
        return totalTicketsByDate;
    }

    public Map<String, Long> findTotalTicketsByTypeAchat() {
        List<Object[]> results = ticketRepository.findTotalTicketsByTypeAchat();
        return results.stream()
                .collect(Collectors.toMap(
                        obj -> String.valueOf(obj[0]),
                        obj -> Long.valueOf(String.valueOf(obj[1]))
                ));
    }
    public Map<String, Long> getTotalNbtsByEventType(Long userId) {
        List<Object[]> results = ticketRepository.findTotalNbtsByEventType(userId);
        return results.stream()
                .collect(Collectors.toMap(
                        obj -> String.valueOf(obj[0]),
                        obj -> Long.valueOf(String.valueOf(obj[1]))
                ));
    }

    public Map<String, Double> findTotalPricesByEventUser(Long userId) {
        List<Object[]> results = ticketRepository.findTotalPricesByEventWithUserId(userId);
        Map<String, Double> totalPricesByEvent = new HashMap<>();
        for (Object[] result : results) {
            String eventId = String.valueOf(result[0]);
            Double totalPrice = (Double) result[1];
            totalPricesByEvent.put(eventId, totalPrice);
        }
        return totalPricesByEvent;
    }



    public Map<String, Double> findTotalPricesByEvent() {
        List<Object[]> results = ticketRepository.findTotalPricesByEvent();
        Map<String, Double> totalPricesByEvent = new HashMap<>();
        for (Object[] result : results) {
            String eventId = String.valueOf(result[0]);
            Double totalPrice = (Double) result[1];
            totalPricesByEvent.put(eventId, totalPrice);
        }

        return totalPricesByEvent;
    }

}
