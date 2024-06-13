package tn.esprit.espritgather.service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Ticket;

import java.io.IOException;
import java.util.List;
public interface IEventService {
    public List<Event> retrieveAllEvents();

    public Event retrieveEvent(Long idEvent);

    public Event addEvent(Event c);

    public void removeEvent(Long idEvent);

    public Event modifyEvent(Event event);
    public void updateEventArchive();

    List<Event> findEventsByPreferences(Long userId);
    public List<Event> findEventsOrderByTotalNbts(Long userId);

    public List<Event> retrieveEventByNameEvent(String name);

    List<Event> retrieveEventsByUser(Long userId);


    public List<Event> findAllEventsOrderedByPriceAsc();
    public List<Event> findAllEventsOrderedByNbt();

    public Event saveEvent(Event event, String imageFile) throws IOException;



}



