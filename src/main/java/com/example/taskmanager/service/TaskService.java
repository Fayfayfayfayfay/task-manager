package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Task> findAll() {
        return repo.findAll();
    }

    @Override
    public Task findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public Task save(Task task) {
        return repo.save(task);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}