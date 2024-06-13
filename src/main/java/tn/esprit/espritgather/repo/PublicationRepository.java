package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Publication;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT p.idPublication, p.nl, p.dl FROM Publication p")
    List<Object[]> findAllWithNlAndDl();

    @Query("SELECT p.nl, p.dl FROM Publication p WHERE p.idPublication = :publicationId")
    Object[] findLikesAndDislikesByPublicationId(@Param("publicationId") Long publicationId);
}
