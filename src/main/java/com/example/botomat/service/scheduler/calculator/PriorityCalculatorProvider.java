package com.example.botomat.service.scheduler.calculator;

import com.example.botomat.model.scheduler.SchedulingAlgo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class PriorityCalculatorProvider {
    private final Map<SchedulingAlgo, PriorityCalculator> priorityCalculators = new EnumMap<>(SchedulingAlgo.class);
    @PostConstruct
    public void init() {
        priorityCalculators.put(SchedulingAlgo.CHAOS, new ChaosCalculator());
        priorityCalculators.put(SchedulingAlgo.UNKNOWN, (task, context) -> Integer.MAX_VALUE);
    }

    public PriorityCalculator getCalculator(SchedulingAlgo algo) {
        return priorityCalculators.getOrDefault(algo, (task, context) -> Integer.MAX_VALUE);
    }
}
