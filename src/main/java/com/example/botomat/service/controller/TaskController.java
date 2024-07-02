package com.example.botomat.service.controller;

import com.example.botomat.model.Task;
import com.example.botomat.service.scheduler.TaskScheduler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class TaskController {
    private final TaskScheduler taskScheduler;

    @PostMapping(value = "/tasks")
    public void createTasks(@RequestBody List<Task> tasks) {
        tasks.forEach(taskScheduler::schedule);
    }

    @PostMapping(value = "/task")
    public void createTask(@Valid @RequestBody Task task) {
        taskScheduler.schedule(task);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Task controller error", e);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>("Task controller error", httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
