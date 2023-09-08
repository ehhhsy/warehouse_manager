package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Product;
import com.hsy.warehouse_manager2.pojo.Purchase;
import com.hsy.warehouse_manager2.pojo.Result;

public interface PurchaseService {

    //添加入库单buy_list
    public Result savePurchase(Purchase purchase);

    //分页查询采购单
    public Page queryPurchase(Purchase purchase, Page page);


    //根据buyId删除采购单
    public Result removePurchaseById(Integer buyId);

    //根据id修改采购数量和实际采购数量
    public Result resetPurchaseById(Purchase purchase);
}
