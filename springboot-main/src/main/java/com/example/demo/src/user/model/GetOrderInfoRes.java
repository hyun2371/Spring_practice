package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderInfoRes {
    private int orderIdx;
    private String store;
    private String menu;
    private String totalPrice;
}
