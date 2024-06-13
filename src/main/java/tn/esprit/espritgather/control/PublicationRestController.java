package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.service.IPublicationService;

import java.util.List;

@Tag(name = "Gestion Publication")
@RestController
@AllArgsConstructor
@RequestMapping("/publication")
@CrossOrigin(origins = "*")
public class PublicationRestController {
    IPublicationService publicationService;

    @Operation(description = "Récupérer toutes les publications de la base de données")
    @GetMapping("/publications")
    public List<Publication> getPublications() {
        List<Publication> publications = publicationService.retrieveAllPublications();
        return publications;
    }

    @GetMapping("/retrieve-publication/{publication-id}")
    public Publication retrievePublication(@PathVariable("publication-id") Long id) {
        Publication publication = publicationService.retrievePublication(id);
        return publication;
    }

    @PostMapping("/add-publication")
    public Publication addPublication(@RequestBody Publication publication) {
        Publication addedPublication = publicationService.addPublication(publication);
        return addedPublication;
    }

    @GetMapping("/all-with-nl-and-dl")
    public ResponseEntity<List<Object[]>> findAllWithNlAndDl() {
        List<Object[]> publicationsWithNlAndDl = publicationService.findAllWithNlAndDl();
        return ResponseEntity.ok(publicationsWithNlAndDl);
    }
    @GetMapping("/{id}/likes-and-dislikes")
    public ResponseEntity<?> getLikesAndDislikesForPublication(@PathVariable("id") Long publicationId) {
        try {
            Object[] likesAndDislikes = publicationService.findLikesAndDislikesByPublicationId(publicationId);
            if (likesAndDislikes == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(likesAndDislikes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des données : " + e.getMessage());
        }
    }

    @DeleteMapping("/remove-publication/{publication-id}")
    public void removePublication(@PathVariable("publication-id") Long id) {
        publicationService.removePublication(id);
    }

    @PutMapping("/modify-publication")
    public Publication modifyPublication(@RequestBody Publication publication) {
        Publication modifiedPublication = publicationService.modifyPublication(publication);
        return modifiedPublication;
    }
}
