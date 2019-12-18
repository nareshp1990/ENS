package com.ens.model.poll;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserSummary implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String profileImageUrl;

}
