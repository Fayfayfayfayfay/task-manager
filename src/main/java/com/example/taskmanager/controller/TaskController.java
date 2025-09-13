package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public Task one(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public Task create(@RequestBody Task task) { return service.save(task); }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task updated) {
        Task task = service.findById(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setStatus(updated.getStatus());
        return service.save(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}