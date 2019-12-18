package com.ens.model.user;

import com.ens.model.location.Area;
import com.ens.model.location.Country;
import com.ens.model.location.District;
import com.ens.model.location.State;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponse implements Serializable {

    private Long id;
    private String userName;
    private String email;
    private String mobileNumber;
    private String profileImageUrl;
    private String fcmRegistrationKey;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Country country;
    private State state;
    private District district;
    private Area area;
}
