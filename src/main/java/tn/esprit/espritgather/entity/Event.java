package tn.esprit.espritgather.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import tn.esprit.espritgather.enumeration.ClubTypes;
import tn.esprit.espritgather.enumeration.TypeTicket;
import org.hibernate.annotations.Type;

import java.sql.Blob;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String nameEvent;
    private String description;
    private Date dateStart;
    private Date dateFinish;
    private String place;
    private String imagePath;
    @Enumerated(EnumType.STRING)

    private ClubTypes typeEvent;
    private boolean archive;

    private double price;



    private int nbt;

    @Enumerated(EnumType.STRING)
    private TypeTicket typeticket;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;






}
