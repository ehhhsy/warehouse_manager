package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.UnitMapper;
import com.hsy.warehouse_manager2.pojo.Unit;
import com.hsy.warehouse_manager2.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.UnitServiceImpl")
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    @Cacheable(key = "'all:unit'")
    @Override
    public List<Unit> findAllUnit() {
        List<Unit> unitList = unitMapper.findAllUnit();
        return unitList;
    }
}
