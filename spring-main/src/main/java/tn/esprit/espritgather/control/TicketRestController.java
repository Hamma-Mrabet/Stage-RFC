package tn.esprit.espritgather.control;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.service.IEventService;
import tn.esprit.espritgather.service.ITicketService;
import tn.esprit.espritgather.service.IUserService;

import java.util.List;

@Tag(name = "Gestion Ticket")
@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
@CrossOrigin(origins = "*")

public class TicketRestController {
    ITicketService ticketService;
    IEventService eventService;
    IUserService userService;
    // http://localhost:8089/espritgather/ticket/retrieve-all-tickets
    @Operation(description = "récupérer toutes les tickets de la base de données")
    @GetMapping("/retrieve-all-tickets")
    public List<Ticket> getTickets() {
        List<Ticket> listTickets = ticketService.retrieveAllTickets();
        return listTickets;
    }
    // http://localhost:8089/espritgather/ticket/retrieve-ticket/8
    @GetMapping("/retrieve-ticket/{ticket-id}")
    public Ticket retrieveTicket(@PathVariable("ticket-id") Long chId) {
        Ticket ticket = ticketService.retrieveTicket(chId);
        return ticket;
    }
    // http://localhost:8089/espritgather/ticket/add-ticket
    @PostMapping("/add-ticket")
    public Ticket addTicket(@RequestBody Ticket c) {
        byte[] qrCode = generateQRCode(c.getIdTicket());
        c.setQrCode(qrCode);
        Ticket ticket = ticketService.addTicket(c);
        return ticket;
    }

    // http://localhost:8089/espritgather/ticket/remove-ticket/{ticket-id}
    @DeleteMapping("/remove-ticket/{ticket-id}")
    public void removeTicket(@PathVariable("ticket-id") Long chId) {
        ticketService.removeTicket(chId);
    }
    // http://localhost:8089/espritgather/ticket/modify-ticket
    @PutMapping("/modify-ticket")
    public Ticket modifyTicket(@RequestBody Ticket c) {
        Ticket ticket = ticketService.modifyTicket(c);
        return ticket;
    }

    @PostMapping("/add-ticket-by-event/{event-id}")
    public Ticket addTicketByEvent(@RequestBody Ticket ticket, @PathVariable("event-id") Long eventId) {
        Event event = eventService.retrieveEvent(eventId);
        byte[] qrCode = generateQRCode(ticket.getIdTicket());
        ticket.setQrCode(qrCode);
        ticket.setEvent(event);
        Ticket addedTicket = ticketService.addTicket(ticket);
        return addedTicket;
    }

    @GetMapping("/totalNbtsByEventType/{user-id}")
    public Map<String, Long> getTotalNbtsByEventType(@PathVariable("user-id") Long userId) {
        return ticketService.getTotalNbtsByEventType(userId);
    }



    @PostMapping("/add-ticket-by-event-user/{user-id}/{event-id}")
    public Ticket addTicketByEventAndUser(@PathVariable("user-id") Long userId,@RequestBody Ticket ticket, @PathVariable("event-id") Long eventId) {
        Event event = eventService.retrieveEvent(eventId);
        User user = userService.retrieveUser(userId);

        ticket.setUser(user);
        byte[] qrCode = generateQRCode(ticket.getIdTicket());
        ticket.setQrCode(qrCode);
        ticket.setEvent(event);
        Ticket addedTicket = ticketService.addTicket(ticket);
        return addedTicket;
    }


    @DeleteMapping("/delete-ticket-by-event/{event-id}/{ticket-id}")
    public ResponseEntity<String> deleteTicketByEvent(@PathVariable("event-id") Long eventId, @PathVariable("ticket-id") Long ticketId) {
        Event event = eventService.retrieveEvent(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketService.retrieveTicket(ticketId);
        if (ticket == null || !ticket.getEvent().getIdEvent().equals(eventId)) {
            return ResponseEntity.notFound().build();
        }

        ticketService.removeTicket(ticketId);
        return ResponseEntity.ok("Ticket with ID " + ticketId + " deleted successfully.");
    }



    @GetMapping("/retrieve-tickets-by-event/{event-id}")
    public List<Ticket> retrieveTicketsByEvent(@PathVariable("event-id") Long eventId) {
        List<Ticket> tickets = ticketService.retrieveTicketsByEvent(eventId);
        return tickets;
    }

    @GetMapping("/retrieve-tickets-by-event-and-user/{user-id}/{event-id}")
    public List<Ticket> retrieveTicketsByEventAndUser(@PathVariable("event-id") Long eventId,@PathVariable("user-id") Long userId) {
        List<Ticket> tickets = ticketService.retrieveTicketsByEventAndUser(eventId,userId);
        return tickets;
    }

    @GetMapping("/total-tickets-by-date")
    public ResponseEntity<Map<String, Long>> getTotalTicketsByDateAchat() {
        Map<String, Long> totalTicketsByDate = ticketService.getTotalTicketsByDateAchat();
        return ResponseEntity.ok(totalTicketsByDate);
    }

    @GetMapping("/total-tickets-by-typeachat")
    public ResponseEntity<Map<String, Long>> getTotalTicketsByTypeAchat() {
        Map<String, Long> totalTicketsByTypeAchat = ticketService.findTotalTicketsByTypeAchat();
        return ResponseEntity.ok(totalTicketsByTypeAchat);
    }

    @GetMapping("/total-prices-by-event")
    public ResponseEntity<Map<String, Double>> getTotalPricesByEvent() {
        Map<String, Double> totalPricesByEvent = ticketService.findTotalPricesByEvent();
        return ResponseEntity.ok(totalPricesByEvent);
    }

    @GetMapping("/totalPricesByEvent/{userId}")
    public ResponseEntity<Map<String, Double>> findTotalPricesByEvent(@PathVariable("userId") Long userId) {
        Map<String, Double> totalPricesByEvent = ticketService.findTotalPricesByEventUser(userId);
        return ResponseEntity.ok(totalPricesByEvent);
    }








    public static byte[] generateQRCode(Long ticketId) {
            try {
                // Créer un objet contenant uniquement les attributs idTicket et nbTs
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(ticketId);

                // Définir la taille du QR code
                int width = 300;
                int height = 300;

                // Créer le map d'indices d'encodage pour définir le niveau de correction d'erreur
                Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                // Créer le QR code
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(jsonData, BarcodeFormat.QR_CODE, width, height, hintMap);

                // Créer une image tampon pour dessiner
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = (Graphics2D) image.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, width, height);
                graphics.setColor(Color.BLACK);

                // Écrire la matrice de bits dans l'image tampon
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (bitMatrix.get(x, y)) {
                            graphics.fillRect(x, y, 1, 1);
                        }
                    }
                }

                // Convertir l'image tampon en tableau d'octets
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // Classe interne pour stocker les données du ticket à encoder dans le QR code
        private static class TicketData {
            private Long idTicket;
            private int nbTs;

            public TicketData(Long idTicket, int nbTs) {
                this.idTicket = idTicket;
                this.nbTs = nbTs;
            }

            public Long getIdTicket() {
                return idTicket;
            }

            public void setIdTicket(Long idTicket) {
                this.idTicket = idTicket;
            }

            public int getNbTs() {
                return nbTs;
            }

            public void setNbTs(int nbTs) {
                this.nbTs = nbTs;
            }
        }
    }