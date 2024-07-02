package com.example.botomat.service.scheduler.calculator;


import com.example.botomat.model.Task;
import com.example.botomat.model.scheduler.SchedulingContext;

import java.util.concurrent.ThreadLocalRandom;

public class ChaosCalculator implements PriorityCalculator {
    @Override
    public int calculatePriority(Task task, SchedulingContext context) {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
