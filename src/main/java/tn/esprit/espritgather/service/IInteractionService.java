package tn.esprit.espritgather.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.entity.Publication;

import java.util.List;
import java.util.Map;

public interface IInteractionService {
    List<Interaction> retrieveAllInteractions();
    Interaction retrieveInteraction(Long interactionId);
    ResponseEntity<Interaction> addInteraction(Interaction interaction, Long idpub,Long idUser);
    void removeInteraction(Long interactionId);
    Interaction modifyInteraction(Interaction interaction);
    Interaction findInteractionByPublicationAndUser(Long idpublication,Long idusr);
}
