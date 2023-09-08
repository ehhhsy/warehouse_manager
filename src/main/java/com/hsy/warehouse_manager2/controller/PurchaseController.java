package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.*;
import com.hsy.warehouse_manager2.service.InStoreService;
import com.hsy.warehouse_manager2.service.PurchaseService;
import com.hsy.warehouse_manager2.service.StoreService;
import com.hsy.warehouse_manager2.until.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    //注入PurchaseService
    @Autowired
    private PurchaseService purchaseService;

    //注入StoreService
    @Autowired
    private StoreService storeService;

    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

//    注入InStoreService
    @Autowired
    private InStoreService inStoreService;


    //添加采购单   /purchase/purchase-add
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase){
        //执行业务
        Result result = purchaseService.savePurchase(purchase);
        return result;
    }

    //查询所有仓库  /purchase/store-list
    @RequestMapping("/store-list")
    public Result showAllStore(){
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }

    //分页查询所有 / 根据条件查询  /purchase/purchase-page-list
    @RequestMapping("/purchase-page-list")
    public Result selectPurchase(Purchase purchase,  Page page){
        //执行业务
        page = purchaseService.queryPurchase(purchase, page);
        return Result.ok(page);
    }

    // /purchase/purchase-delete/{buyId} 根据id删除采购单
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchaseById(@PathVariable Integer buyId){
        //执行业务
        Result result = purchaseService.removePurchaseById(buyId);
        return result;
    }

    ///purchase/purchase-update 根据id修改采购数量和实际采购数量
    @RequestMapping("/purchase-update")
    public Result updatePurchaseById(@RequestBody Purchase purchase){
        //执行业务
        Result result = purchaseService.resetPurchaseById(purchase);
        return result;
    }

    ///purchase/in-warehouse-record-add
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase, @RequestHeader String token){
        //拿取当前对象，作为inshore对象的createBy
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        //创建inStore对象
        InStore inStore = new InStore();
        inStore.setCreateBy(userId);
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());

        //执行业务
        Result result = inStoreService.addInStore(inStore, purchase.getBuyId());
        return result;
    }

    // /purchase/exportTable 导出数据
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,Purchase purchase){
        //分页查询
        page = purchaseService.queryPurchase(purchase, page);
        List<?> resultList = page.getResultList();
        return Result.ok(resultList);
    }
}
