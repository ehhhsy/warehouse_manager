package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.pojo.InStore;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Store;
import com.hsy.warehouse_manager2.service.InStoreService;
import com.hsy.warehouse_manager2.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hsy.warehouse_manager2.page.Page;

import java.util.List;

@RequestMapping("/instore")
@RestController
public class InStoreController {

    @Autowired
    private InStoreService inStoreService;

    @Autowired
    private StoreService storeService;

    //查询所有仓库url接口 /instore/store-list 之前写过，在storeService里
    @RequestMapping("/store-list")
    public Result StoreList(){
        List<Store> storeList = storeService.queryAllStore();
            return Result.ok(storeList);
    }

    /**
     *   /instore/instore-page-list?storeId=&productName=&startTime=&endTime=&pageSize=5&pageNum=1&totalNum=0
     *   查询所有入库单并分页 或者 根据仓库id、商品名称、起止时间查询入库单并分页
     */
    @RequestMapping("/instore-page-list")
    public Result queryInStore(Page page, InStore inStore){
        //执行业务
        page = inStoreService.queryInstorePage(page, inStore);
        return Result.ok(page);
    }

    //  /instore/instore-confirm 确认入库
    @RequestMapping("/instore-confirm")
    public Result ConfirmInStore(@RequestBody InStore inStore){
        //执行业务
        Result result = inStoreService.confirmInStore(inStore);
        return result;
    }

//    /instore/exportTable 导出数据
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,InStore inStore){
        //分页查询
        page = inStoreService.queryInstorePage(page, inStore);
        List<?> resultList = page.getResultList();
        return Result.ok(resultList);
    }
}
