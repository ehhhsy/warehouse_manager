package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;
@Mapper
public interface BrandMapper {

    //查询所有品牌
    public List<Brand> findAllList();
}
