package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Store;
import com.hsy.warehouse_manager2.service.StashService;
import com.hsy.warehouse_manager2.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StashController {
    @Autowired
    private StashService stashService;

    @Autowired
    private StoreService storeService;
    /**
     * /statistics/store-invent -- 查询每个仓库存储的商品的数量;
     */
    @RequestMapping("/statistics/store-invent")
    public Result allStash(){
        Result result = stashService.queryEachStash();
        return result;
    }

    /**
     * /store/store-page-list:查询所有仓库/根据条件查询仓库
     */
    @RequestMapping("/store/store-page-list")
    public Result getAllStore(Store store, Page page){
        //执行service
        page = stashService.selectAllStash(page, store);
        return Result.ok(page);
    }

    /**
     * /store/store-num-check?storeNum=#{storeNum} 验证仓库编号是否存在
     */
    @RequestMapping("/store/store-num-check")
    public Result verifyStoreNum(String storeNum){
        //调用service
        Result result = stashService.verifyStoreNum(storeNum);
        return result;
    }

    /**
     * /store/store-add 添加仓库
     * {storeName: "2222", storeNum: "33", storeAddress: "3333", concat: "3333", phone: "222"}
     */
    @RequestMapping("/store/store-add")
    public Result addStash(@RequestBody Store store){
        //执行业务st
        Result result = stashService.addStash(store);
        return result;
    }

    /**
     * /store/store-delete/{storeId}
     */
    @RequestMapping("/store/store-delete/{storeId}")
    public Result deleteStash(@PathVariable Integer storeId){
        //执行业务
        Result result = stashService.deleteStashById(storeId);
        return result;
    }

    /**
     * /store/store-update  修改仓库
     */
    @RequestMapping("/store/store-update")
    public Result modifyStash(@RequestBody Store store){
        //执行业务
        Result result = stashService.modifyStash(store);
        return result;
    }
    /**
     * /store/exportTable 导出数据
     */
    @RequestMapping("/store/exportTable")
    public Result exportTable(Page page, Store store){
        //分页查询仓库
        page = stashService.selectAllStash(page, store);
        //拿到当前页数据
        List<?> resultList = page.getResultList();
        //响应
        return Result.ok(resultList);
    }

}
