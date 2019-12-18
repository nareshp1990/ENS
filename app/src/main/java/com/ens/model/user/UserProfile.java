package com.ens.model.user;

import java.io.Serializable;
import java.time.LocalDate;

public class UserProfile implements Serializable {

    private Long id;
    private Gender gender;
    private LocalDate dateOfBirth;

}
