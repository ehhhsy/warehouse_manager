package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.BrandMapper;
import com.hsy.warehouse_manager2.pojo.Brand;
import com.hsy.warehouse_manager2.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.BrandServiceImpl")
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    //查询所有品牌,需要缓存
    @Cacheable(key = "'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {
        //执行mapper
        List<Brand> brandList = brandMapper.findAllList();
        return brandList;
    }
}
