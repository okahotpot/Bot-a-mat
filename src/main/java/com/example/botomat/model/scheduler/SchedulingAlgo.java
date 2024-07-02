package com.example.botomat.model.scheduler;

import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
@Getter
public enum SchedulingAlgo {
    UNKNOWN,
    LOWEST_ETA_FIRST,
    FIFO,
    CHAOS;

    public static final Map<String, SchedulingAlgo> ALGO_MAPPINGS = Arrays.stream(SchedulingAlgo.values())
            .collect(toMap(algo -> algo.toString().toLowerCase(Locale.ROOT), Function.identity()));

    public static SchedulingAlgo getAlgo(String algoName){
        return ALGO_MAPPINGS.getOrDefault(getCleanInput(algoName),UNKNOWN);
    }

    private static String getCleanInput(String input){
        return input.trim().toLowerCase(Locale.ROOT);
    }
}
