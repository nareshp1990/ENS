package com.ens.model.api;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class DateAudit {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}
