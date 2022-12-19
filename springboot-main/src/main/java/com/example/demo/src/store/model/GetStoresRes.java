package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoresRes {
    private int storeIdx;
    private String type;
    private String name;
    private int minimumCost;
    private float ratingAvg;
    private List<GetStoreImgRes> storeImgList;
}
