package tn.esprit.espritgather.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.entity.Volunteer;
import tn.esprit.espritgather.enumeration.EventSkill;
import tn.esprit.espritgather.repo.TaskRepository;
import tn.esprit.espritgather.repo.UserRepository;
import tn.esprit.espritgather.repo.VolunteerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VolunteerServiceImpl implements IVolunteerService {
    VolunteerRepository volunteerRepository;

    final  private TaskRepository taskRepository;


    final  private UserRepository userRepository;
    public List<Volunteer> retrieveAllVolunteers() { return volunteerRepository.findAll(); }
    public Volunteer retrieveVolunteer(Long volunteerId) {
        return volunteerRepository.findById(volunteerId).get();
    }
    public Volunteer addVolunteer(Volunteer c) {
        return volunteerRepository.save(c);
    }
    public List<Volunteer> findVolunteerByTask_Event_IdEvent(Long idTask){
        return volunteerRepository.findVolunteerByTask_Event_IdEvent(idTask);}
    public void removeVolunteer(Long volunteerId) {
        volunteerRepository.deleteById(volunteerId);
    }
    public Volunteer modifyVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public List<Volunteer> retrieveVolunteersByTask(Long idTask) {
        return volunteerRepository.findVolunteerByTask_IdTask(idTask);}


    //////////////////////////////////////////////
    @Override
    public Volunteer createVolunteer(Volunteer volunteer, Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        volunteer.setTask(task);
        volunteer.setUser(user);
        return volunteerRepository.save(volunteer);
    }



    @Override
    public List<Volunteer> retrieveVolunteersByUser(User user) {
        return volunteerRepository.findVolunteerByUser(user);
    }

    @Override
    public List<Volunteer> retrieveVolunteersByTask(Task task) {
        return volunteerRepository.findVolunteerByTask(task);
    }

    @Override
    public List<Volunteer> findVolunteerByUser_IdUserAndTask_IdTask(Long user,Long task) {
        return volunteerRepository.findVolunteerByUser_IdUserAndTask_IdTask(user,task);
    }

    public Map<String, Long> findUserVolunteerCounts() {
        List<Object[]> results = volunteerRepository.findUserVolunteerCounts();
        return results.stream()
                .collect(Collectors.toMap(
                        obj -> String.valueOf(obj[0]),
                        obj -> Long.valueOf(String.valueOf(obj[1]))
                ));
    }





    @Override
    public List<Volunteer> getAllVolunteersOrderedBySkills() {
        // Retrieve all volunteers
        List<Volunteer> volunteers = volunteerRepository.findAll();

        // Sort volunteers based on the skills of the task and volunteer
        volunteers.sort(Comparator.comparingInt(volunteer -> {
            // Get the skills of the task associated with the volunteer
            Set<EventSkill> taskSkills = volunteer.getTask().getSkills();

            // Get the skills of the volunteer
            Set<EventSkill> volunteerSkills = volunteer.getSkills();

            // Calculate the total number of matching skills
            long matchingSkillsCount = taskSkills.stream()
                    .filter(volunteerSkills::contains)
                    .count();

            return (int) matchingSkillsCount;
        }));

        return volunteers;
    }

    //////////////////////////hathi trateb tab+/////////////////////////////
    @Override
    public List<Volunteer> getAllVolunteersOrderedBySkillsbyTask(Long idTask) {
        // Retrieve all volunteers
        List<Volunteer> volunteers = volunteerRepository.findVolunteerByTask_IdTask(idTask);

        // Sort volunteers based on the skills of the task and volunteer
        volunteers.sort(Comparator.comparingInt(volunteer -> {
            // Get the skills of the task associated with the volunteer
            Set<EventSkill> taskSkills = volunteer.getTask().getSkills();

            // Get the skills of the volunteer
            Set<EventSkill> volunteerSkills = volunteer.getSkills();

            // Calculate the total number of matching skills
            long matchingSkillsCount = taskSkills.stream()
                    .filter(volunteerSkills::contains)
                    .count();

            return (int) matchingSkillsCount;
        }));

        // Create a new list and reverse the order of the elements
        List<Volunteer> reversedVolunteers = new ArrayList<>(volunteers);
        Collections.reverse(reversedVolunteers);
        return reversedVolunteers;
    }





    //Task.numberVolunteer
    ///volunteer.approuvement=true par le ordre de getAllVolunteersOrderedBySkillsbyTask  (count = Task.numberVolunteer)

    @Override
    public void approveVolunteersForTask(Long taskId) {
        // Obtenir le nombre de volontaires désiré pour la tâche
        int numberVolunteersDesired = volunteerRepository.findNumberVolunteersByidTask(taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        // Retrieve all volunteers for the specified task
        List<Volunteer> volunteers = getAllVolunteersOrderedBySkillsbyTask(taskId);
        // Approve only the number of volunteers equal to task.numberVolunteer
        for (int i = 0; i < Math.min(volunteers.size(), task.getNumberVolunteer()); i++) {
            Volunteer volunteer = volunteers.get(i);
            volunteer.setApprouvement(true); // Set approval to true
            volunteerRepository.save(volunteer); // Update the volunteer in the database

        }
        volunteers = volunteerRepository.findVolunteerByTask_IdTask(taskId);
        //nombre de volontaires approuvés
        int numberVolunteersApproved = (int) volunteers.stream()
                .filter(Volunteer::isApprouvement)
                .count();

        if(task.getNumberVolunteer()>=0) {
            //breake si task.getNumberVolunteer() - numberVolunteersApproved < 0
            int nbrVolunteer = task.getNumberVolunteer() - numberVolunteersApproved;
            if (nbrVolunteer < 0) {
                nbrVolunteer = 0;
            }
            task.setNumberVolunteer(nbrVolunteer);
            taskRepository.save(task);
        }
    }



    @Override
    public int retrieveNumberVolunteersByIdTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return task.getNumberVolunteer();
    }






}