package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInteraction;

    private Boolean liked;
    private Boolean dislike;



    @ManyToOne
    @JoinColumn(name = "publications")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;



}
