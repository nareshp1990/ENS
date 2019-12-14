package com.ens.model.api;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PagedResponse<T> implements Serializable {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

}