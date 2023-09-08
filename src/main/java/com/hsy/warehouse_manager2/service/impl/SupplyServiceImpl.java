package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.SupplyMapper;
import com.hsy.warehouse_manager2.pojo.Supply;
import com.hsy.warehouse_manager2.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.SupplyServiceImpl")
@Service
public class SupplyServiceImpl implements SupplyService {
    @Autowired
    private SupplyMapper supplyMapper;

    @Cacheable(key = "'all::supply'")
    @Override
    public List<Supply> queryAllSupply() {
        List<Supply> supplyList = supplyMapper.findAllSupply();
        return supplyList;
    }
}
