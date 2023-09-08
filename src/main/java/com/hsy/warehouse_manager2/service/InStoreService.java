package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.InStore;
import com.hsy.warehouse_manager2.pojo.Result;
import org.apache.ibatis.annotations.Param;

public interface InStoreService {
    //添加入库单二:添加入库单in_store
    public Result addInStore(InStore inStore,Integer buyId);

    //分页查询入库单
    public Page queryInstorePage(Page page,InStore inStore);

    //确认入库
    public Result confirmInStore(InStore inStore);
}
