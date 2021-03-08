package com.david.engclimatesummary.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PagingRequest {

    private int start;
    private int length;
    private int draw;
    private String minDate;
    private String maxDate;
    private List<Order> order;
    private List<Column> columns;
    private Search search;
}
