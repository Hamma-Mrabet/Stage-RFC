package tn.esprit.espritgather.control;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.service.ITaskService;
import tn.esprit.espritgather.service.IUserService;
import tn.esprit.espritgather.service.IVolunteerService;
import tn.esprit.espritgather.entity.Volunteer;

import java.util.List;
import java.util.Map;

@Tag(name = "Gestion Volunteer")
@RestController
@AllArgsConstructor
@RequestMapping("/volunteer")
@CrossOrigin(origins = "*")
public class VolunteerRestController {
    IVolunteerService volunteerService;
    ITaskService taskService;
    IUserService userService;

    // http://localhost:8089/espritgather/volunteer/retrieve-all-volunteers
    @Operation(description = "récupérer toutes les volunteer de la base de données")
    @GetMapping("/retrieve-all-volunteers")
    public List<Volunteer> getvolunteers() {
        List<Volunteer> listVolunteers = volunteerService.retrieveAllVolunteers();
        return listVolunteers;
    }
    // http://localhost:8089/espritgather/volunteer/retrieve-volunteer/8
    @GetMapping("/retrieve-volunteer/{volunteer-id}")
    public Volunteer retrievevolunteer(@PathVariable("volunteer-id") Long chId) {
        Volunteer volunteer = volunteerService.retrieveVolunteer(chId);
        return volunteer;
    }
    // http://localhost:8089/espritgather/volunteer/add-volunteer
    @PostMapping("/add-volunteer")
    public Volunteer addvolunteer(@RequestBody Volunteer c) {
        Volunteer volunteer = volunteerService.addVolunteer(c);
        return volunteer;
    }
    // http://localhost:8089/espritgather/volunteer/remove-volunteer/{volunteer-id}
    @DeleteMapping("/remove-volunteer/{volunteer-id}")
    public void removevolunteer(@PathVariable("volunteer-id") Long chId) {
        volunteerService.removeVolunteer(chId);
    }
    // http://localhost:8089/espritgather/volunteer/modify-volunteer
    @PutMapping("/modify-volunteer")
    public Volunteer modifyvolunteer(@RequestBody Volunteer c) {
        Volunteer volunteer = volunteerService.modifyVolunteer(c);
        return volunteer;
    }


    @PostMapping("/add-volunteer-by-task/{task-id}")
    public Volunteer addVolunteerByTask(@RequestBody Volunteer volunteer, @PathVariable("task-id") Long idTask) {
        Task task = taskService.retrieveTask(idTask);

        volunteer.setTask(task);

        Volunteer addedVolunteer = volunteerService.addVolunteer(volunteer);
        return addedVolunteer;
    }


    //////////////////////////////////////////

    /////////// http://localhost:8089/volunteer/create-volunteer/1////////////////////////////////////////
    @PostMapping("/create-volunteer/{taskId}/{userId}")
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer, @PathVariable("taskId") Long taskId,@PathVariable("userId") Long userId) {
        //////////////id user conecte b spring sercuity (getUserAuthentication)//////////////
        //User user = getUserAuth()
        //Long userId = user.id
        User user = userService.retrieveUser(userId);

        userId = user.getIdUser();

        return volunteerService.createVolunteer(volunteer, taskId, userId);
    }





    // http://localhost:8089/volunteer/retrieve-volunteer-by-task/1
    @GetMapping("/retrieve-volunteer-by-task/{taskid}")
    public List<Volunteer> retrieveVolunteersByTask(@PathVariable("taskid") Long taskId) {
        Task task = taskService.retrieveTask(taskId);
        return volunteerService.retrieveVolunteersByTask(task);
    }

    // http://localhost:8089/volunteer/retrieve-volunteer-by-user/1
    @GetMapping("/retrieve-volunteer-by-user/{userid}")
    public List<Volunteer> retrieveVolunteersByUser(@PathVariable("userid") Long userId) {
        User user = taskService.retrieveUser(userId);
        return volunteerService.retrieveVolunteersByUser(user);
    }

    // http://localhost:8089/volunteer/get-all-volunteers-ordered-by-skills
    @GetMapping("/get-all-volunteers-ordered-by-skills")
    public List<Volunteer> getAllVolunteersOrderedBySkills() {
        return volunteerService.getAllVolunteersOrderedBySkills();
    }
    //////////////////////hiya eli trateb////////////////////////////////
    // http://localhost:8089/volunteer/get-all-volunteers-ordered-by-skills-by-task/1
    @GetMapping("/get-all-volunteers-ordered-by-skills-by-task/{idTask}")
    public List<Volunteer> getAllVolunteersOrderedBySkillsbyTask(@PathVariable("idTask") Long idTask) {
        return volunteerService.getAllVolunteersOrderedBySkillsbyTask(idTask);
    }

    @PutMapping("/approve/{taskId}")
    public ResponseEntity<?> approveVolunteersForTask(@PathVariable Long taskId) {
        try {
            volunteerService.approveVolunteersForTask(taskId);
            return ResponseEntity.ok().body("Volunteers approved successfully for task with ID: " + taskId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving volunteers: " + e.getMessage());
        }
    }

    @GetMapping("/retrieve-number-volunteers/{taskId}")
    public int retrieveNumberVolunteersByIdTask(@PathVariable Long taskId) {
        return volunteerService.retrieveNumberVolunteersByIdTask(taskId);
    }

    @GetMapping("/retrieve-vol-user/{userId}/{taskId}")
    public List findVolunteerByTaskAndUser(@PathVariable Long userId,@PathVariable Long taskId) {
        return volunteerService.findVolunteerByUser_IdUserAndTask_IdTask(userId,taskId);
    }

    @GetMapping("/user-volunteer-counts")
    public Map<String, Long> getUserVolunteerCounts() {
        return volunteerService.findUserVolunteerCounts();
    }


    @GetMapping("/retrievevolunteersbyevent/{eventId}")
    public List<Volunteer> findVolunteerByTask_Event_IdEvent(@PathVariable Long eventId) {
        return volunteerService.findVolunteerByTask_Event_IdEvent(eventId);
    }

}
