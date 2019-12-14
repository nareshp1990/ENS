package com.ens.model.user;

import com.ens.model.api.DateAudit;

import java.io.Serializable;
import java.util.UUID;

public class User extends DateAudit implements Serializable {

    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobileNumber;
    private String profileImageUrl;
    private String fcmRegistrationKey;
    private UserProfile userProfile;
}
