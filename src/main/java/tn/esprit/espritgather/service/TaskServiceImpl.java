package tn.esprit.espritgather.service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.EventSkill;
import tn.esprit.espritgather.enumeration.StatusT;
import tn.esprit.espritgather.repo.EventRepository;
import tn.esprit.espritgather.repo.TaskRepository;
import tn.esprit.espritgather.repo.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {
    TaskRepository taskRepository;

    private  final EventRepository eventRepository;

    private  final UserRepository userRepository;


    public List<Task> retrieveAllTasks() {
        return taskRepository.findAll();
    }

    public Task retrieveTask(Long taskId) {
        return taskRepository.findById(taskId).get();
    }



    public void removeTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task modifyTask(Task task) {
        return taskRepository.save(task);
    }

    @Scheduled(fixedRate = 60000)
    public void updateStatusTask() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.getTaskFinish() == null || new Date().after(task.getTaskFinish())) {
                task.setStatus(StatusT.COMPLETED);
                taskRepository.save(task);
            }
            if (new Date().before(task.getTaskFinish()) && new Date().after((task.getTaskStart()))) {
                task.setStatus(StatusT.IN_PROGRESS);
                taskRepository.save(task);
            }
            if (new Date().before(task.getTaskStart())) {
                task.setStatus(StatusT.TODO);
                taskRepository.save(task);
            }
        }
    }

    public Map<String, Long> findTotalTasksByStatus() {
        List<Object[]> results = taskRepository.findTotalTasksByStatus();
        return results.stream()
                .collect(Collectors.toMap(
                        obj -> String.valueOf(obj[0]),
                        obj -> Long.valueOf(String.valueOf(obj[1]))
                ));
    }


    @Override
    public Task createTask(Task task, Long eventId) {
        // Retrieve the event from the database
       /* Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));*/
        Event event = new Event();
        event.setIdEvent(eventId);
        // Set the task's event
        task.setEvent(event);
        return taskRepository.save(task);
    }


    @Override
    public List<Task> retrieveTasksByEvent(Long eventId) {
        return taskRepository.findTasksByEventIdEvent(eventId);
    }

    @Override
    public User retrieveUser(Long userId) {
        return userRepository.findByIdUser(userId);

    }

    public Map<EventSkill, Integer> countSkillOccurrences(List<Task> tasks) {
        Map<EventSkill, Integer> skillCounts = new HashMap<>();
        for (Task task : tasks) {
            for (EventSkill skill : task.getSkills()) {
                skillCounts.put(skill, skillCounts.getOrDefault(skill, 0) + 1);
            }
        }
        return skillCounts;
    }


}