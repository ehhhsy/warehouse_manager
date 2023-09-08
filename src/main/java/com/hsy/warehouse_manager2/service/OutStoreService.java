package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.OutStore;
import com.hsy.warehouse_manager2.pojo.Result;

public interface OutStoreService {

    //添加出库单的方法
    public Result saveOutStore(OutStore outStore);

    //分页查询出库表
    public Page queryAllStore(Page page, OutStore outStore);

    //确认出库的业务方法
    public Result confirmOutStore(OutStore outStore);

}
