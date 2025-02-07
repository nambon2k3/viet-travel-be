package com.fpt.capstone.tourism.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PagingDTO<T> implements Serializable {
    private int page;
    private int size;
    private long total;
    private T items;
}
