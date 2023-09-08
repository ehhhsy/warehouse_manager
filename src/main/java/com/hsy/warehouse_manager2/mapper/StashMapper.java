package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Statistics;
import com.hsy.warehouse_manager2.pojo.Store;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.hsy.warehouse_manager2.page.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 统计查询和仓库管理
 */
@Mapper
public interface StashMapper {

    //查询每个仓库的商品数量，用于统计作图
    public List<Statistics> findStoreCount();

    //查询所有仓库总行数
    public int selectCountStash(Store store);

    //分页查询所有仓库
    public List<Store> selectResult(@Param("page") Page page, @Param("store") Store store);

    //验证仓库编号是否存在，(添加仓库)
    public Store verifyStoreNum(@Param("storeNum") String StoreNum);

    //添加仓库
    public int addStash( Store store);

    //根据storeId删除仓库
    public int deleteStashById(Integer storeId);

    //修改
    public int updateStash(Store store);
}


