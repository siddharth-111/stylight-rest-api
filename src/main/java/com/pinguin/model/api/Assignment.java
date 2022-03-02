package com.pinguin.model.api;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Assignment {
    private UUID issueId;
    private UUID developerId;
}
