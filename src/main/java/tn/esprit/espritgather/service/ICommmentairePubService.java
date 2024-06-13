package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.CommentairePub;
import tn.esprit.espritgather.entity.Publication;


import java.util.List;

public interface ICommmentairePubService {
    List<CommentairePub> retrieveAllCommentaires();
    CommentairePub retrieveCommentaire(Long commentaireId);
    CommentairePub addCommentaire(CommentairePub commentaire);
    void removeCommentaire(Long commentaireId);
    CommentairePub modifyCommentaire(CommentairePub commentaire);

    List<CommentairePub> retrieveAllCommentairesByPublication(Long idPublication);
}
