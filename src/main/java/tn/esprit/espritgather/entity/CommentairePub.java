package tn.esprit.espritgather.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentairePub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommentaire;
    private String body;

    @ManyToOne
    @JoinColumn(name = "publication")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

}
