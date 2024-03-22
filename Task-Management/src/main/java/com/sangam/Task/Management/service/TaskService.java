package com.sangam.Task.Management.service;

import com.sangam.Task.Management.Constants.TaskStatus;
import com.sangam.Task.Management.Entity.TasksDetails;
import com.sangam.Task.Management.Request.TaskRequest;
import com.sangam.Task.Management.Response.TaskResponse;
import com.sangam.Task.Management.exception.TaskNotFoundException;
import com.sangam.Task.Management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> findAllTasks() {

        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public Page<TaskResponse> findAllTasks(Pageable pageable) {

        Page<TasksDetails> tasksPage = taskRepository.findAll(pageable);
        List<TaskResponse> responseList = tasksPage.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, tasksPage.getTotalElements());
    }


    public TaskResponse findTaskById(Long id) {

        TasksDetails task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        return mapToDTO(task);
    }

    public TaskResponse createTask(TaskRequest taskRequest) {

        TasksDetails newTask = mapToEntity(taskRequest);
        // Set the status of the new task to OPEN, ignoring the provided status
        newTask.setStatus(TaskStatus.OPEN);
        taskRepository.save(newTask);
        return mapToDTO(newTask);
    }


    public TaskResponse updateTask(Long id, TaskStatus taskStatus) {

        TasksDetails task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        task.setStatus(taskStatus);
        taskRepository.save(task);
        return mapToDTO(task);
    }


    public void deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with ID: " + id);
        }
        TasksDetails tasksDetails = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        if(tasksDetails.getStatus().equals(TaskStatus.COMPLETED)){
            taskRepository.deleteById(id);

        }

    }

    private TaskResponse mapToDTO(TasksDetails task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        return response;
    }

    private TasksDetails mapToEntity(TaskRequest request) {
        TasksDetails task = new TasksDetails();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        return task;
    }
}
