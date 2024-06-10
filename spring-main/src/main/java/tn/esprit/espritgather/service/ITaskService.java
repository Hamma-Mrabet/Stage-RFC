package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.EventSkill;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITaskService {
    public List<Task> retrieveAllTasks();
    public Task retrieveTask(Long idTask);

    public void removeTask(Long idTask);
    public Task modifyTask(Task task);

    Task createTask(Task task, Long eventId);

    List<Task> retrieveTasksByEvent(Long eventId);

    User retrieveUser(Long userId);

    Map<String, Long> findTotalTasksByStatus();

    public Map<EventSkill, Integer> countSkillOccurrences(List<Task> tasks);
}