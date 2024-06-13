package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.entity.Volunteer;

import java.util.List;
import java.util.Map;

public interface IVolunteerService {
    public List<Volunteer> retrieveAllVolunteers();
    public Volunteer retrieveVolunteer(Long idVolunteer);
    public Volunteer addVolunteer(Volunteer c);
    public void removeVolunteer(Long idVolunteer);
    public Volunteer modifyVolunteer(Volunteer volunteer);

    Volunteer createVolunteer(Volunteer volunteer, Long taskId, Long userId);

    public List<Volunteer> findVolunteerByTask_Event_IdEvent(Long idTask);
    List<Volunteer> retrieveVolunteersByUser(User user);

    List<Volunteer> retrieveVolunteersByTask(Task task);

    List<Volunteer> getAllVolunteersOrderedBySkills();
    List<Volunteer> findVolunteerByUser_IdUserAndTask_IdTask(Long user,Long task);

    List<Volunteer> getAllVolunteersOrderedBySkillsbyTask(Long idTask);

    void approveVolunteersForTask(Long taskId);

    int retrieveNumberVolunteersByIdTask(Long taskId);
    Map<String, Long> findUserVolunteerCounts();

}