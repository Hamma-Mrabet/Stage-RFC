package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.espritgather.entity.Management;
import tn.esprit.espritgather.entity.Ticket;

import java.util.List;

public interface ManagementRepository extends JpaRepository<Management,Long> {
    Management findManagementByEventIdEvent(Long eventId);

      @Query("SELECT m.bloc, m.classe, COUNT(m.idManagement) FROM Management m WHERE m.bloc!= null AND m.classe != null GROUP BY m.bloc, m.classe ")
      List<Object[]> calculateManagementCounts();






}
