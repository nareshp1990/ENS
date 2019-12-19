package com.ens.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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

    private Long country;
    private Long state;
    private Long district;
    private Long area;
}
