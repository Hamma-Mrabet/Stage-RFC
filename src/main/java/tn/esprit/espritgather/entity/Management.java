package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import tn.esprit.espritgather.enumeration.Bloc;
import tn.esprit.espritgather.enumeration.Classe;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Management {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idManagement;
    private String details;
    private Boolean approuvement;

    @Enumerated(EnumType.STRING)
    private Bloc bloc;

    @Enumerated(EnumType.STRING)
    private Classe classe;

    @OneToOne
    @JoinColumn(name = "event")
    private Event event;


}
