package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Store;

public interface StashService {

    //查询每个仓库存储的商品的数量;
    public Result queryEachStash();

    //分页查询所有仓库（仓库管理)
    public Page selectAllStash(Page page, Store store);

    //验证仓库编号是否存在，(添加仓库)
    public Result verifyStoreNum(String storeNum);

    //添加仓库
    public Result addStash(Store store);

    //删除仓库
    public Result deleteStashById(Integer storeId);

    //修改仓库
    public Result modifyStash(Store store);
}
