package com.netflix.review_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Show {
    private long id;

    public Show() {}
    public Show(long id) {this.id = id;}
}
