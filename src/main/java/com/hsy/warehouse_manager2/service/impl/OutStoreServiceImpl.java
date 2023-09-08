package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.OutStoreMapper;
import com.hsy.warehouse_manager2.mapper.ProductMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.OutStore;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService {
    //注入OutStoreMapper
    @Autowired
    private OutStoreMapper outStoreMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //添加出库单的业务方法
    @Override
    public Result saveOutStore(OutStore outStore) {
        //添加出库单
        int i = outStoreMapper.insertOutStore(outStore);
        if(i>0){
            return Result.ok("添加出库单成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加出库单失败！");
    }

    //分页查询出库单的业务方法
    @Override
    public Page queryAllStore(Page page, OutStore outStore) {
        //查询出库单行数
        int count = outStoreMapper.outStoreCount(outStore);

        //分页查询出库单
        List<OutStore> storeList = outStoreMapper.outStorePage(page, outStore);

        page.setTotalNum(count);
        page.setResultList(storeList);
        return page;
    }

    //确认出库业务方法
    @Transactional
    @Override
    public Result confirmOutStore(OutStore outStore) {
        //注意：1.首先判断商品库存是否充足
        //现有库存
        int invent = productMapper.findInventById(outStore.getProductId());

        //如果 现有库存<出货总量
        if (invent<outStore.getOutNum()){
            return Result.err(Result.CODE_ERR_BUSINESS,"商品库存不足");
        }

        //2.将出库单状态改为1已出库
        int i = outStoreMapper.setIsOutById(outStore.getOutsId());

        if (i>0){
            //3.修改商品的库存 -- 减库存
            int j = productMapper.setInventById(outStore.getProductId(), -outStore.getOutNum());
            if (j>0){
                return Result.ok("出库成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"确认出库失败.?");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"确认出库失败.?");
    }
}
