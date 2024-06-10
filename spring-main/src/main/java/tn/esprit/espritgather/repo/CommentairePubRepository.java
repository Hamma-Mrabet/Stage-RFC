package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.espritgather.entity.CommentairePub;
import tn.esprit.espritgather.entity.Publication;

import java.util.List;

public interface CommentairePubRepository extends JpaRepository<CommentairePub,Long> {
    List<CommentairePub> getAllByPublication(Publication publication);
}
