package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import tn.esprit.espritgather.enumeration.Note;
import tn.esprit.espritgather.enumeration.ServiceType;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;

    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private Note note;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    private Date datevu;
    private Date dateFeedback;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

}
