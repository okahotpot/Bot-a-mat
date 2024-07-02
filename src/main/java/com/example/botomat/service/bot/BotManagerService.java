package com.example.botomat.service.bot;

import com.example.botomat.model.Bot;
import com.example.botomat.model.Task;
import com.example.botomat.model.leaderboard.ScoreContext;
import com.example.botomat.service.scheduler.TaskScheduler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Marry bots and tasks together
 */
@Service
@Slf4j
public class BotManagerService {
    private final BotCreator factory;
    private final TaskScheduler taskScheduler;
    private final AtomicInteger activeBots = new AtomicInteger(0);
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final ScoreContext scoreContext = new ScoreContext();


    @Autowired
    public BotManagerService(BotCreator creator,TaskScheduler scheduler){
        this.factory = creator;
        this.taskScheduler = scheduler;
    }

    public void createBot(Bot bot){
        factory.getBot(bot);
        threadPool.submit(() -> doTask(bot));
        activeBots.incrementAndGet();
    }

    public ScoreContext getScoring(){
        return scoreContext;
    }

    private void doTask(Bot bot) {
        long startTime = System.currentTimeMillis();
        try {
            while (true) {
                Optional<Task> taskOptional = taskScheduler.getNextTask(bot.getPreferredTaskNames());
                if (taskOptional.isPresent()) {
                    Task task = taskOptional.get();
                    log.info("{} bot is starting task: {}",bot.getName(),task.getDescription());
                    Thread.sleep(task.getEstimatedTimeInMillis());
                    log.info("{} bot is starting task: {}",bot.getName(),task.getDescription());
                    //update score context
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            long endTime = System.currentTimeMillis();
            reportResults(bot, endTime - startTime);
            if (activeBots.decrementAndGet() == 0) {
                log.info("All tasks completed by all bots.");
            }
        }
    }

    private void reportResults(Bot bot, long timeTaken) {
        log.info("Reporting results");
        //update score context
    }


}
