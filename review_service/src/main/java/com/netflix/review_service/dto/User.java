package com.netflix.review_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;

    public User() {}
    public User(long id) {this.id = id;}
}
