package com.example.botomat.service.scheduler;

public interface Scheduler<T> {
    void schedule(T task);
}
