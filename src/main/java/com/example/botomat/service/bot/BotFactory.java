package com.example.botomat.service.bot;

import com.example.botomat.model.Bot;
import com.example.botomat.model.BotType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

/**
 * The BotManager class is responsible for managing bots. It implements the BotFactoryPattern interface.
 */
@Component
public class BotFactory implements BotCreator {

    HashMap<String, Bot> BotMap = new HashMap<>(10);

    @FunctionalInterface
    public interface BotProvider {
        Bot createBot(String name, String type);
    }
    private final BotProvider botProvider =
            (name, type) -> Bot.builder()
                    .name(name)
                    .type(BotType.getType(type))
                    .build();

    public Bot getBot(String name, String type) {
        return BotMap.computeIfAbsent(name,k->botProvider.createBot(name,type));
    }

    @Override
    public Bot getBot(Bot bot) {
        String name = bot.getName();
        BotMap.put(name,bot);
        return bot;
    }

    @Override
    public Optional<Bot> getBot(String name) {
        return Optional.ofNullable(BotMap.getOrDefault(name,null));
    }


}
