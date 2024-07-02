package com.example.botomat.service.bot;

import com.example.botomat.model.Bot;
import com.example.botomat.model.Task;

public interface BotManager {
    void doTask(Bot bot, Task task);
}
