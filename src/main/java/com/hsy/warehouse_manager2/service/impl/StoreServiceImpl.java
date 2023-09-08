package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.StoreMapper;
import com.hsy.warehouse_manager2.pojo.Store;
import com.hsy.warehouse_manager2.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.StoreServiceImpl")
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;
    //查询所有仓库的业务方法，需要缓存
    @Override
    @Cacheable(key="'all:store'")
    public List<Store> queryAllStore() {
        List<Store> storeList = storeMapper.findAllStore();
        return storeList;
    }
}
