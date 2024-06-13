package tn.esprit.espritgather.service;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.config.CloudinaryService;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.repo.EventRepository;
import tn.esprit.espritgather.repo.TicketRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static tn.esprit.espritgather.control.EventRestController.uploadDirectory;
@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements IEventService {
    EventRepository eventRepository;
    TicketRepository ticketRepository;
    CloudinaryService CloudinaryService;
    public List<Event> retrieveAllEvents() { return eventRepository.findAll(); }
    public Event retrieveEvent(Long eventId) {
        return eventRepository.findById(eventId).get();
    }
    public Event addEvent(Event c) {
        return eventRepository.save(c);
    }
    public void removeEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
    public Event modifyEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event saveEvent(Event event, String imageFile) throws IOException {
            event.setImagePath(imageFile);
            eventRepository.save(event);


        return eventRepository.save(event);
    }
    @Scheduled(fixedRate = 60000)
    public void updateEventArchive() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            if (event.getDateFinish() == null || new Date().after(event.getDateFinish())) {
                event.setArchive(true);
                eventRepository.save(event);
            }
        }
    }

    public List<Event> findEventsByPreferences(Long userId) {
        return eventRepository.findEventsByPreferences(userId);
    }


    public List<Event> findEventsOrderByTotalNbts(Long userId) {
        List<Object[]> eventCounts = ticketRepository.findTotalNbtsByEventType1(userId);
        List<Event> events = new ArrayList<>();

        for (Object[] eventCount : eventCounts) {
            Event event = (Event) eventCount[0];
            events.add(event);
        }

        return events;
    }


    //@Scheduled(fixedRate = 60000)
    public List<Event> retrieveEventsByUser(Long userId) {
        updateEventArchive();
        return eventRepository.findEventsByUserIdUser(userId);
    }




    public List<Event> retrieveEventByNameEvent(String name){
        return eventRepository.findEventsByNameEvent(name);
    }

    public List<Event> findAllEventsOrderedByPriceAsc() {
        return eventRepository.findAllByOrderByPriceAsc();
    }

    public List<Event> findAllEventsOrderedByNbt() {
        return eventRepository.findAllEventsOrderedByNbt();
    }


}