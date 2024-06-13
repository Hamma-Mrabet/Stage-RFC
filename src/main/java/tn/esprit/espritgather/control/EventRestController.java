package tn.esprit.espritgather.control;
import io.github.classgraph.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

import nonapi.io.github.classgraph.fileslice.reader.ClassfileReader;
import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.config.CloudinaryService;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.TypeTicket;
import tn.esprit.espritgather.repo.TicketRepository;
import tn.esprit.espritgather.service.IEventService;
import tn.esprit.espritgather.service.ITicketService;

import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.FileSystemResource;
import tn.esprit.espritgather.service.IUserService;


@Tag(name = "Gestion Event")
@RestController
@AllArgsConstructor
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventRestController {
    IEventService eventService;
    ITicketService ticketService;
    TicketRepository ticketRepository;
    IUserService userService;
    CloudinaryService cloudinaryService;

    // public static String uploadDirectory = "C:/Users/Admin/angular/src/assets/images/";
    public static String uploadDirectory = "C:/Users/ameni/OneDrive/Bureau/angular/src/assets/images/";


    // http://localhost:8089/espritgather/event/retrieve-all-events
   @Operation(description = "récupérer toutes les event de la base de données")
    @GetMapping("/retrieve-all-events")
    public List<Event> getevents() {
        List<Event> listEvents = eventService.retrieveAllEvents();
        return listEvents;
    }

    // http://localhost:8089/espritgather/event/retrieve-event/8
    @GetMapping("/retrieve-event/{event-id}")
    public Event retrieveevent(@PathVariable("event-id") Long chId) {
        Event event = eventService.retrieveEvent(chId);
        return event;
    }


    @PostMapping("/add-event")
    public ResponseEntity<Event> addEvent(@ModelAttribute Event event, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String imagePath = cloudinaryService.uploadImage(imageFile);

            Event savedEvent = eventService.saveEvent(event,imagePath);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/preferences/{userId}")
    public List<Event> findEventsByPreferences(@PathVariable Long userId) {
        return eventService.findEventsByPreferences(userId);
    }

    @GetMapping("/preferences/please/{userId}")
    public List<Event> findEventsOrderByTotalNbts(@PathVariable Long userId) {
        return eventService.findEventsOrderByTotalNbts(userId);
    }

    @PostMapping("/add-event/{user-id}")
    public Event addEventByUser(@PathVariable("user-id") Long userId, @ModelAttribute Event event, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        User user = userService.retrieveUser(userId);
        event.setUser(user);
        System.out.println("Date Start received: " + event.getDateStart());
        System.out.println("Date Finish received: " + event.getDateFinish());
        String imagePath = cloudinaryService.uploadImage(imageFile);
        Event savedEvent = eventService.saveEvent(event, imagePath);
        System.out.println("Date Start received: " + event.getDateStart());
        System.out.println("Date Finish received: " + event.getDateFinish());
        return savedEvent;
    }


    @GetMapping("/retrieve-events-by-user/{user-id}")
    public List<Event> retrieveEventsByUser(@PathVariable("user-id") Long userId) {
        eventService.updateEventArchive();

        List<Event> events = eventService.retrieveEventsByUser(userId);
        return events;
    }




    @DeleteMapping("/remove-event/{event-id}")
    public void removeevent( @PathVariable("event-id")  Long eventId) {
        List<Ticket> tickets = ticketRepository.findTicketsByEventIdEvent(eventId);
        for (Ticket ticket : tickets) {
            ticketRepository.delete(ticket);
        }
        eventService.removeEvent(eventId);
    }

    // http://localhost:8089/espritgather/event/modify-event
    @PutMapping("/modify-event")
    public Event modifyevent(@RequestBody Event c) {
        c.setArchive(false);

        Event event = eventService.modifyEvent(c);
        return event;
    }

    @GetMapping("/retrieve-event-by-name/{event-name}")
    public List<Event> retrieveEventByNameEvent(@PathVariable("event-name") String name) {
        List<Event> listEvents = eventService.retrieveEventByNameEvent(name);
        return listEvents;
    }

    @GetMapping("/ordered-by-price")
    public ResponseEntity<List<Event>> getAllEventsOrderedByPriceAsc() {
        List<Event> events = eventService.findAllEventsOrderedByPriceAsc();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/ordered-by-nbt")
    public ResponseEntity<List<Event>> findAllEventsOrderedByNbt() {
        List<Event> events = eventService.findAllEventsOrderedByNbt();
        return ResponseEntity.ok(events);
    }












}