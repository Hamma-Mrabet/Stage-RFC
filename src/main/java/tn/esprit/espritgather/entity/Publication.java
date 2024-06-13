package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublication;
    private String body;
    private Date datePublication;

    private int nl;
    private int dl;
    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;



    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
