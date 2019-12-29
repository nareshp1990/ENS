package com.ens.model.user;

import com.ens.model.location.Area;
import com.ens.model.location.Country;
import com.ens.model.location.District;
import com.ens.model.location.State;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
    private UserType userType;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;
    private Country country;
    private State state;
    private District district;
    private Area area;
}
