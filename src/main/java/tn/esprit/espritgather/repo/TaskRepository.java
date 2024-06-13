package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.espritgather.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findTasksByEventIdEvent(Long eventId);

    @Query("SELECT t.status, SUM(t.idTask) FROM Task t GROUP BY t.status")
    List<Object[]> findTotalTasksByStatus();
}