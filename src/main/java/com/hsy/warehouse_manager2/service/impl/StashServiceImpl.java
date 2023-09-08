package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.StashMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Statistics;
import com.hsy.warehouse_manager2.pojo.Store;
import com.hsy.warehouse_manager2.service.StashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StashServiceImpl implements StashService {
    @Autowired
    private StashMapper stashMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查询每个仓库存储的商品的数量;
    @Override
    public Result queryEachStash() {
        List<Statistics> statisticsList = stashMapper.findStoreCount();
        return Result.ok(statisticsList);
    }

    //分页查询所有仓库（仓库管理)
    @Override
    public Page selectAllStash(Page page, Store store) {
        //1.先查总行数
        int count = stashMapper.selectCountStash(store);

        //2.分页查询
        List<Store> storeList = stashMapper.selectResult(page, store);

        page.setTotalNum(count);
        page.setResultList(storeList);
        return page;
    }

    //验证仓库编号是否存在，(添加仓库)
    @Override
    public Result verifyStoreNum(String storeNum) {
        //执行业务
        Store store = stashMapper.verifyStoreNum(storeNum);
        return Result.ok(store==null);
    }

    //添加仓库

    @Override
    public Result addStash(Store store) {
        int i = stashMapper.addStash(store);
        if (i>0){
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.StoreServiceImpl::all:store");
            return Result.ok("添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
    }


    ////删除仓库
    @Override
    public Result deleteStashById(Integer storeId) {
        int i = stashMapper.deleteStashById(storeId);
        if (i>0){
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.StoreServiceImpl::all:store");
            return Result.ok("删除成功<");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败!?");
    }

    //修改仓库
    @Override
    public Result modifyStash(Store store) {
        int i = stashMapper.updateStash(store);
        if (i>0){
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.StoreServiceImpl::all:store");
            return Result.ok("修改成功<");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败!?");
    }

}
