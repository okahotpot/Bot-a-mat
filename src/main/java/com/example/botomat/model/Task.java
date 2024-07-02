package com.example.botomat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task implements Comparable<Task> {
    private final String description;
    private final int estimatedTimeInMillis;
    private int priority;

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }
}