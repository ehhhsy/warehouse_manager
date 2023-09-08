package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.Place;

import java.util.List;

public interface PlaceService {

    //查询所有产地 -- 给添加商品提供的
    public List<Place> queryAllPlace();
}
