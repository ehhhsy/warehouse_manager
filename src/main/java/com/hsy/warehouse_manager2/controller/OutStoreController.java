package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.CurrentUser;
import com.hsy.warehouse_manager2.pojo.OutStore;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Store;
import com.hsy.warehouse_manager2.service.OutStoreService;
import com.hsy.warehouse_manager2.service.StoreService;
import com.hsy.warehouse_manager2.until.TokenUtils;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/outstore")
@RestController
public class OutStoreController {

    @Autowired
    private OutStoreService outStoreService;

    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    //注入StoreService
    @Autowired
    private StoreService storeService;

    /**
     * 添加出库单的url接口/outstore/outstore-add
     *
     * @RequestBody OutStore outStore将添加的出库单信息的json数据封装到参数OutStore对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        outStore.setCreateBy(createBy);

        //执行业务
        Result result = outStoreService.saveOutStore(outStore);

        //响应
        return result;
    }
    /**
     * /outstore/store-list 查询所有仓库 用于  (作为条件查询的下拉仓库)
     */
    @RequestMapping("/store-list")
    public Result getAllStore(){
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }
    /**
     *  /outstore/outstore-page-list   查询所有出库单并分页 或者 根据仓库id 商品名称 起止时间 是否出库查询出库单并分页;
     */
    @RequestMapping("/outstore-page-list")
    public Result queryOutStore(Page page,OutStore outStore){
        //执行业务
        page = outStoreService.queryAllStore(page, outStore);
        return Result.ok(page);
    }
    ///outstore/outstore-confirm 确认出库
    @RequestMapping("/outstore-confirm")
    public Result outStoreConfirm(@RequestBody OutStore outStore){
        //执行业务
        Result result = outStoreService.confirmOutStore(outStore);
        return result;
    }

    ///outstore/exportTable 导出数据
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,OutStore outStore){
        //执行业务
        page = outStoreService.queryAllStore(page, outStore);
        List<?> resultList = page.getResultList();
        return Result.ok(resultList);
    }
}
