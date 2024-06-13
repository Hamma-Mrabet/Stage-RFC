package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;

import java.util.List;

public interface RecrutementRepository extends JpaRepository<Recrutement, Long> {
    List<Recrutement> findByNiveauGreaterThan(int niveau);

    List<Recrutement> findRecrutementByUserIdUser(long id);

}
