package com.ens.model.poll;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class UserSummary implements Serializable {

    private UUID id;
    private String username;
    private String email;
    private String profileImageUrl;

}
