package com.example.botomat.service.scheduler;

import com.example.botomat.model.Task;
import com.example.botomat.model.scheduler.SchedulingAlgo;
import com.example.botomat.model.scheduler.SchedulingContext;
import com.example.botomat.service.scheduler.calculator.PriorityCalculator;
import com.example.botomat.service.scheduler.calculator.PriorityCalculatorProvider;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Provide next task for the given bot
 * Schedule the tasks
 */
@Service
@Getter
public class TaskScheduler implements Scheduler<Task> {

    private PriorityCalculator calculator;
    private final BlockingQueue<Task> taskQueue =  new PriorityBlockingQueue<>();

    private final SchedulingAlgo schedulingAlgo = SchedulingAlgo.FIFO;
    private final PriorityCalculatorProvider calculatorProvider;

    Supplier<SchedulingContext> contextProvider = SchedulingContext::new;

    private static final int POLLING_TIME_OUT = 1;
    private static final int STREAM_TASK_WINDOW = 5;
    public TaskScheduler(PriorityCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    @PostConstruct
    public void init(){
        calculator = calculatorProvider.getCalculator(schedulingAlgo);
    }

    @Override
    public void schedule(Task task) {
        SchedulingContext context = contextProvider.get();
        int priority = calculator.calculatePriority(task,context);
        task.setPriority(priority);
        taskQueue.add(task);
    }


    public Optional<Task> getNextTask(List<String> preferredTaskNames) {
        try {
            Predicate<Task> taskNameFilter = task -> preferredTaskNames.contains(task.getDescription());

            Optional<Task> preferredTask = taskQueue.stream()
                    .limit(STREAM_TASK_WINDOW)
                    .filter(taskNameFilter)
                    .findFirst();

            if (preferredTask.isPresent()) {
                taskQueue.remove(preferredTask.get());
                return preferredTask;
            } else {
                return Optional.ofNullable(taskQueue.poll(POLLING_TIME_OUT, TimeUnit.SECONDS));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Task> getNextTask() {
        return getNextTask(Collections.emptyList());
    }
}
