package com.ens.model.user;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRequest implements Serializable {

    private String userName;
    private String email;
    private String mobileNumber;
    private String profileImageUrl;
    private String fcmRegistrationKey;
    private String password;
    private Gender gender;
    private LocalDate dateOfBirth;

    private Long countryId;
    private Long stateId;
    private Long districtId;
    private Long areaId;
}
