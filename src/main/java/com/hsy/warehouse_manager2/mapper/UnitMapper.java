package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UnitMapper {

    //查询所有单位 -- 给添加商品提供的
    public List<Unit> findAllUnit();
}
