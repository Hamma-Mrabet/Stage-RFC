package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.CommentairePub;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.CommentairePubRepository;
import tn.esprit.espritgather.repo.PublicationRepository;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor

public class CommentairePubServiceImpl implements ICommmentairePubService{

    CommentairePubRepository commentairePubRepository;
    IPublicationService publicationService;
    IUserService userService;

    @Override
    public List<CommentairePub> retrieveAllCommentaires() {
        return commentairePubRepository.findAll();
    }

    @Override
    public CommentairePub retrieveCommentaire(Long commentaireId) {
        return commentairePubRepository.findById(commentaireId).orElse(null);
    }

    @Override
    public CommentairePub addCommentaire(CommentairePub commentaire) {
        return commentairePubRepository.save(commentaire);
    }

    @Override
    public void removeCommentaire(Long commentaireId) {
        commentairePubRepository.deleteById(commentaireId);
    }

    @Override
    public CommentairePub modifyCommentaire(CommentairePub commentaire) {
        return commentairePubRepository.save(commentaire);
    }

    @Override
    public List<CommentairePub> retrieveAllCommentairesByPublication(Long idPublication) {
        Publication pub = publicationService.retrievePublication(idPublication);
        return commentairePubRepository.getAllByPublication(pub);
    }
}
