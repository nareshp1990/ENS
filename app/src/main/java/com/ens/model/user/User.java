package com.ens.model.user;

import com.ens.model.api.DateAudit;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User extends DateAudit implements Serializable {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobileNumber;
    private String profileImageUrl;
    private String fcmRegistrationKey;
    private UserProfile userProfile;
    private UserType userType;

}
