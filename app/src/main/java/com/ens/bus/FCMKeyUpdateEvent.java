package com.ens.bus;

import com.ens.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMKeyUpdateEvent {

    private User user;

}
