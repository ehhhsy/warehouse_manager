package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Place;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface PlaceMapper {

    //查询所有产地 -- 给添加商品提供的
    public List<Place> findAllPlace();
}
