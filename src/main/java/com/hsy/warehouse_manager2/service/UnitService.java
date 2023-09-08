package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.Unit;

import java.util.List;

public interface UnitService {

//    查询所有单位  给添加商品提供的
    public List<Unit> findAllUnit();
}
