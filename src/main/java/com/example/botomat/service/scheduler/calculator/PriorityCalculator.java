package com.example.botomat.service.scheduler.calculator;


import com.example.botomat.model.Task;
import com.example.botomat.model.scheduler.SchedulingContext;

@FunctionalInterface
public interface PriorityCalculator {
    int calculatePriority(Task task, SchedulingContext context);
}