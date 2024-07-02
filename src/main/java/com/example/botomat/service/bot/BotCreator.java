package com.example.botomat.service.bot;


import com.example.botomat.model.Bot;

import java.util.Optional;

/**
 * The BotFactoryPattern interface is responsible for defining the methods to create and retrieve bots.
 */
public interface BotCreator {
    Bot getBot(String name, String type);

    Bot getBot(Bot bot);

    Optional<Bot> getBot(String name);
}
