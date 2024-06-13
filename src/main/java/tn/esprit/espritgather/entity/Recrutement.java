package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;

import java.util.Date;
import java.util.Set;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Recrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecrutement;
    private String title;
    private String description;
    private Date dateStart;
    private Date dateFinish;

    private int niveau;

    private boolean archive;


    @ElementCollection

    private Set<Skill> requiredSkills;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;







}
