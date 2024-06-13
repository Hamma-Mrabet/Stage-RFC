package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.entity.User;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    Interaction findInteractionByPublicationAndUser(Publication publication, User user);
}
