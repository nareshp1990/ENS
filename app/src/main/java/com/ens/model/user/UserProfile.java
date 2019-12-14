package com.ens.model.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class UserProfile implements Serializable {

    private UUID id;
    private Gender gender;
    private LocalDate dateOfBirth;

}
