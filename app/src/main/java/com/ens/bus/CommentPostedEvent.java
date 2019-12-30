package com.ens.bus;

import com.ens.model.api.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostedEvent {

    private ApiResponse apiResponse;

}
