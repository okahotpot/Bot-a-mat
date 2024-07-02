package com.example.botomat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
public class Bot {
    private final String name;
    private final BotType type;
    @Builder.Default
    private List<String> preferredTaskNames = Collections.emptyList();
}
