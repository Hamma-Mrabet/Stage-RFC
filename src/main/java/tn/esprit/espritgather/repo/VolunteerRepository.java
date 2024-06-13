package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.entity.Volunteer;

import java.util.List;

public interface VolunteerRepository  extends JpaRepository<Volunteer,Long> {
    List<Volunteer> findVolunteerByTask_IdTask(Long taskId);

    List<Volunteer> findVolunteerByUser_IdUser(Long userId);



    List<Volunteer> findVolunteerByTask_Event_IdEvent(Long eventId);

    List<Volunteer> findVolunteerByTask(Task task);

    List<Volunteer> findVolunteerByUser(User user);

    List<Volunteer> findVolunteerByUser_IdUserAndTask_IdTask(Long user,Long task);

    @Query("SELECT COUNT(v) FROM Volunteer v WHERE v.task.idTask = :taskId")
    int findNumberVolunteersByidTask(@Param("taskId") Long taskId);

    @Query("SELECT v.user.lastName, COUNT(v) FROM Volunteer v GROUP BY v.user ORDER BY COUNT(v) DESC")
    List<Object[]> findUserVolunteerCounts();


}