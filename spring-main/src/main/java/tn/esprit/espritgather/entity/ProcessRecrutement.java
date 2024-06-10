package tn.esprit.espritgather.entity;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProcessRecrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProcessRecrutement;
    private String imagePath;

    private Date interviewDate;
    private String whyToJoin;
    private String availability;
    private boolean integratedInOtherClubs;
    private List<String> otherClubs;

    @Getter
    @Setter
    private boolean approved;




    @ElementCollection

    private Set<Skill> skills;
    @ManyToOne
    @JoinColumn(name = "recrutement")
    private Recrutement recrutement;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public boolean getApproved() {
        return approved;
    }


    public boolean isApproved() {
        return approved;
    }

}


