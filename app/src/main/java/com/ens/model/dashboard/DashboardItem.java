package com.ens.model.dashboard;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardItem implements Serializable {

    private Long id;
    private String imageUrl;
    private String description;

}
