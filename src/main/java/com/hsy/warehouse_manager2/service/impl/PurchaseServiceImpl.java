package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.PurchaseMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Purchase;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;

    //添加采购单
    @Override
    public Result savePurchase(Purchase purchase) {
        //执行mapper
        int i = purchaseMapper.insertPurchase(purchase);
        if (i > 0) {
            return Result.ok("添加成功>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加失败!?");
    }

    //分页查询采购单
    @Override
    public Page queryPurchase(Purchase purchase, Page page) {
        //查询采购单总行数
        int purchaseCount = purchaseMapper.selectPurchaseCount(purchase);

        //分页查询采购单
        List<Purchase> purchaseList = purchaseMapper.selectPurchasePage(page, purchase);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(purchaseCount);
        page.setResultList(purchaseList);

        return page;
    }

    //根据id删除采购单
    @Override
    public Result removePurchaseById(Integer buyId) {
        int i = purchaseMapper.deletePurchaseByBid(buyId);
        if (i>0){
            return Result.ok("删除采购单成功>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除采购单失败");
    }


    //修改采购单数量和实际采购单数量
    @Override
    public Result resetPurchaseById(Purchase purchase) {
        int i = purchaseMapper.updatePurchaseById(purchase);
        if (i>0){
            return Result.ok("修改采购单成功>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改采购单失败");
    }
}
