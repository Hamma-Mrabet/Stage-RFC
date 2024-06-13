package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import tn.esprit.espritgather.enumeration.TypePay;
import tn.esprit.espritgather.enumeration.TypeTicket;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;
    private int nbts;

    private Date dateAchat;

    @Enumerated(EnumType.STRING)
    private TypePay typePay;
    @Lob
    @Column(length = 1048576)
    private byte[] qrCode;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;




}