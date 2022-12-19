package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewsReq {
    private int userIdx;
    private int storeIdx;
    private int menuIdx;
    private int rating;
    private String comment;
}
