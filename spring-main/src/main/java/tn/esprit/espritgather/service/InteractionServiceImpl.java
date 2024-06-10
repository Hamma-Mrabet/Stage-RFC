package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.InteractionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InteractionServiceImpl implements IInteractionService {
    private final InteractionRepository interactionRepository;
    private IPublicationService publicationI;

    @Override
    public List<Interaction> retrieveAllInteractions() {
        return interactionRepository.findAll();
    }

    @Override
    public Interaction retrieveInteraction(Long interactionId) {
        return interactionRepository.findById(interactionId).orElse(null);
    }







    /*@Override
    public ResponseEntity<Map<String, Object>> addInteraction(Interaction interaction, Long idpub, Long idusr) {
        User u = new User();
        u.setIdUser(idusr);  // l u tbadalha b find user by id
        if(interactionRepository.findInteractionByPublicationAndUser(publicationI.retrievePublication(idpub),u)!=null){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "L'interaction existe déjà");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            interaction.setPublication(publicationI.retrievePublication(idpub));
            interaction.setUser(u);
            interactionRepository.save(interaction);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Opération réussie");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }*/
    @Override
    public ResponseEntity<Interaction> addInteraction(Interaction interaction, Long idpub, Long idusr) {
        User u = new User();
        u.setIdUser(idusr);  // l u tbadalha b find user by id
        Interaction interaction1 = interactionRepository.findInteractionByPublicationAndUser(publicationI.retrievePublication(idpub),u);
        if(interaction1!=null){
            interaction.setPublication(publicationI.retrievePublication(idpub));
            interactionRepository.save(interaction);
            return ResponseEntity.status(HttpStatus.OK).body(interaction1);
        }
        else{
            interaction.setPublication(publicationI.retrievePublication(idpub));
            interaction.setUser(u);
            interactionRepository.save(interaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(interaction);
        }
    }

    @Override
    public void removeInteraction(Long interactionId) {
        interactionRepository.deleteById(interactionId);
    }

    @Override
    public Interaction modifyInteraction(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    @Override
    public Interaction findInteractionByPublicationAndUser(Long idpub,Long idusr) {
        User u = new User();
        u.setIdUser(idusr);
        return interactionRepository.findInteractionByPublicationAndUser(publicationI.retrievePublication(idpub),u);
        // l u tbadalha b find user by id
    }
}
