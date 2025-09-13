package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;


import java.util.List;

public interface ITaskService {

    List<Task> findAll();

    Task findById(Long id);

    Task save(Task task);

    void delete(Long id);

}
