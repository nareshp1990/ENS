package com.ens.bus;

import com.ens.model.content.ContentResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadedEvent {

    private ContentResponse contentResponse;

}
