package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.InStoreMapper;
import com.hsy.warehouse_manager2.mapper.ProductMapper;
import com.hsy.warehouse_manager2.mapper.PurchaseMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.InStore;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.InStoreService;
import com.hsy.warehouse_manager2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {
    @Autowired
    private InStoreMapper inStoreMapper;

    @Autowired
    private PurchaseMapper purchaseMapper;
    ////添加入库单二:添加入库单in_store

    @Autowired
    private ProductMapper productMapper;

    @Transactional
    @Override
    public Result addInStore(InStore inStore,Integer buyId) {
        //二是修改采购单（buy_list）的is_in 表示入库
        int i = inStoreMapper.insertInStore(inStore);
        if (i>0){   //添加成功
            //修改is_in,一是向入库表（in_store）添加信息
            int j = purchaseMapper.resetIsInById(buyId);
            if (j>0){
                return Result.ok("is_in也修改,添加入库单成功");
            }
            return Result.ok("失败了");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加入库单失败啊！");
    }

    //分页查询入库单的方法
    @Override
    public Page queryInstorePage(Page page,InStore inStore) {
        //查询入库单行数
        int count = inStoreMapper.selectInStoreCount(inStore);
        //分页查询入库单
        List<InStore> storeList = inStoreMapper.selectInStorePage(page, inStore);
        //封装分页信息
        page.setTotalNum(count);
        page.setResultList(storeList);

        return page;
    }

    //确认入库
    @Transactional
    @Override
    public Result confirmInStore(InStore inStore) {
        //1.将入库单状态改为已入库
        int i = inStoreMapper.setIsInById(inStore.getInsId());
        if (i>0){
            //2.增加商品的库存
            int j = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if (j>0){
                return Result.ok("修改成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"失败!?");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"失败!?");
    }
}
