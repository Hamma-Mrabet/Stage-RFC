package tn.esprit.espritgather.entity;


import lombok.*;
import jakarta.persistence.*;
import tn.esprit.espritgather.enumeration.Equip;
import tn.esprit.espritgather.enumeration.Metric;
import tn.esprit.espritgather.enumeration.TypeEquip;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipmenet;
    @Enumerated(EnumType.STRING)
    private Equip equipement;
    @Enumerated(EnumType.STRING)
    private TypeEquip typeequip;
    @Enumerated(EnumType.STRING)
    private Metric metric;
    private Float quantite;
    private String other;
    private String details;
    private float price;
    private Boolean approuvement;
    private Date datemeeting;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

}