package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Supply;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;
@Mapper
public interface SupplyMapper {

    //查询所有供应商 -- 给添加商品提供的
    public List<Supply> findAllSupply();
}
