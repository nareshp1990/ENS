package com.ens.model.poll;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Poll implements Serializable {

    private UUID id;
    private String question;
    private List<Choice> choices;
    private UserSummary createdBy;
    private LocalDateTime creationDateTime;
    private Instant expirationDateTime;
    private Boolean isExpired;
    private UUID selectedChoice;
    private Long totalVotes;

}