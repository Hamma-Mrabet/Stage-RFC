package tn.esprit.espritgather.control;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Volunteer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.enumeration.EventSkill;
import tn.esprit.espritgather.repo.EventRepository;
import tn.esprit.espritgather.service.ITaskService;

import java.util.List;
import java.util.Map;

@Tag(name = "Gestion Task")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/task")
public class TaskRestController {
    ITaskService taskService;


    private final EventRepository eventRepository;



    // http://localhost:8089/espritgather/task/retrieve-all-tasks
    @GetMapping("/retrieve-all-tasks")
    public List<Task> gettasks() {
        return taskService.retrieveAllTasks();
    }
    // http://localhost:8089/espritgather/task/retrieve-task/8
    @GetMapping("/retrieve-task/{task-id}")
    public Task retrievetask(@PathVariable("task-id") Long chId) {
        Task task = taskService.retrieveTask(chId);
        return task;
    }





    // http://localhost:8089/task/createtaskskill








    // http://localhost:8089/espritgather/task/remove-task/{task-id}
    @DeleteMapping("/remove-task/{task-id}")
    public void removetask(@PathVariable("task-id") Long chId) {
        taskService.removeTask(chId);
    }
    // http://localhost:8089/espritgather/task/modify-task
    @PutMapping("/modify-task")
    public Task modifytask(@RequestBody Task c) {
        Task task = taskService.modifyTask(c);
        return task;
    }



    //////////////////////////////////////////////////////////////
    //////////////////////// http://localhost:8089/task/createtask/{idevent}//////////////////////////

    @PostMapping("/createtask/{idevent}")
    public Task createtask(@RequestBody Task c, @PathVariable("idevent") Long idevent) {
        return taskService.createTask(c, idevent);
    }

    // http://localhost:8089/task/retrieve-tasks-by-event/{idevent}
    @GetMapping("/retrieve-tasks-by-event/{idevent}")
    public List<Task> retrieveTasksByEvent(@PathVariable("idevent") Long idevent) {
        return taskService.retrieveTasksByEvent(idevent);
    }


    @GetMapping("/retrieve-user/{userId}")
    public ResponseEntity<?> retrieveUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(taskService.retrieveUser(userId), HttpStatus.OK);
    }

    @GetMapping("/retrieve-user/{taskId}")
    public ResponseEntity<?> retrieveTask(@PathVariable("taskId") Long taskId) {
        return new ResponseEntity<>(taskService.retrieveTask(taskId), HttpStatus.OK);
    }

    @GetMapping("/skills/count")
    public Map<EventSkill, Integer> getSkillOccurrenceCounts() {
        List<Task> tasks = taskService.retrieveAllTasks();
        Map<EventSkill, Integer> skillCounts = taskService.countSkillOccurrences(tasks);
        return skillCounts;
    }


    @GetMapping("/total-tasks-by-status")
    public ResponseEntity<Map<String, Long>> getTotalStatus() {
        Map<String, Long> totalTicketsByTypeAchat = taskService.findTotalTasksByStatus();
        return ResponseEntity.ok(totalTicketsByTypeAchat);
    }

}

