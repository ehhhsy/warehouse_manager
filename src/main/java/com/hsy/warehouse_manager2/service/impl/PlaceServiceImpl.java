package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.PlaceMapper;
import com.hsy.warehouse_manager2.pojo.Place;
import com.hsy.warehouse_manager2.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.PlaceServiceImpl")
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceMapper placeMapper;

    @Cacheable(key = "'all:place'")
    @Override
    public List<Place> queryAllPlace() {
        List<Place> placeList = placeMapper.findAllPlace();
        return placeList;
    }
}
