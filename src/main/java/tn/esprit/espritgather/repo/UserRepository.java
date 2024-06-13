package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.espritgather.entity.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {


    User findByIdUser(Long userId);

    @Query("SELECT niveau, COUNT(niveau) FROM User WHERE role = 'user' GROUP BY niveau")
    List<Object[]> countUsersByNiveauClasse();

    @Query("SELECT YEAR(u.dateCreation), MONTH(u.dateCreation), COUNT(u) FROM User u GROUP BY YEAR(u.dateCreation), MONTH(u.dateCreation)")
    List<Object[]> countUsersByCreationMonth();

    Optional<User> findBymail(String mail);
    Optional<User> findByPasswordResetToken(String token);
}