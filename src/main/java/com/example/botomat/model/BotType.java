package com.example.botomat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum BotType {
    UNKNOWN("unknown"),
    UNIPEDAL("unipedal"),
    BIPEDAL("bipedal"),
    QUADRUPEDAL("quadrupedal"),
    ARACHNID("arachnid"),
    RADIAL("radial"),
    AERONAUTICAL("aeronautical");

    private final String name;

    public static final Map<String, BotType> BOT_TYPE_MAPPINGS = Arrays.stream(BotType.values())
            .collect(toMap(BotType::getName, Function.identity()));

    public static BotType getType(String typeStr){
        return BOT_TYPE_MAPPINGS.getOrDefault(getCleanInput(typeStr),UNKNOWN);
    }

    private static String getCleanInput(String input){
        return input.trim().toLowerCase(Locale.ROOT);
    }
    @Override
    public String toString() {
        return name;
    }
}
