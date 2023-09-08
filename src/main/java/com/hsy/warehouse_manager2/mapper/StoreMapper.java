package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Store;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;
@Mapper
public interface StoreMapper {

    //查询所有仓库的方法
    public List<Store> findAllStore();
}
