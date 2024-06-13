package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.CommentairePub;
import tn.esprit.espritgather.service.ICommmentairePubService;

import java.util.List;

@Tag(name = "Gestion CommentairePub")
@RestController
@RequestMapping("/commentaire")
@CrossOrigin(origins = "*")

@AllArgsConstructor
public class CommentairePubController {
    private ICommmentairePubService commmentairePubService;

    @Operation(description = "Récupérer toutes les commentaires de la base de données")
    @GetMapping("/commentaires")
    public List<CommentairePub> getCommentaires() {
        List<CommentairePub> commentairePubs = commmentairePubService.retrieveAllCommentaires();
        return commentairePubs;
    }

    @GetMapping("/commentaires/{id-publication}")
    public List<CommentairePub> getCommentairesByPublication(@PathVariable("id-publication") Long idPub) {
        List<CommentairePub> commentairePubs = commmentairePubService.retrieveAllCommentairesByPublication(idPub);
        return commentairePubs;
    }

    @GetMapping("/retrieve-commentaire/{commentaire-id}")
    public CommentairePub retrieveCommentaire(@PathVariable("commentaire-id") Long id) {
        CommentairePub commentairePub = commmentairePubService.retrieveCommentaire(id);
        return commentairePub;
    }

    @PostMapping("/add-commentaire")//Long idUser, Long idPublication
    public CommentairePub addCommentaire(@RequestBody CommentairePub commentairePub) {
        CommentairePub addedCommentaire = commmentairePubService.addCommentaire(commentairePub);
        return addedCommentaire;
    }

    @DeleteMapping("/remove-commentaire/{commentaire-id}")
    public void removeCommentaire(@PathVariable("commentaire-id") Long id) {
        commmentairePubService.removeCommentaire(id);
    }

    @PutMapping("/modify-commentaire")
    public CommentairePub modifyCommentaire(@RequestBody CommentairePub commentairePub) {
        CommentairePub modifiedCommentaire = commmentairePubService.modifyCommentaire(commentairePub);
        return modifiedCommentaire;
    }

}
