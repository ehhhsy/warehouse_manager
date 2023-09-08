package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    //添加采购单
    public int insertPurchase(Purchase purchase);

    //查询分页总行数
    public int selectPurchaseCount(Purchase purchase);

    //分页查询
    public List<Purchase> selectPurchasePage(@Param("page") Page page, @Param("purchase") Purchase purchase);

    //根据id删除采购单
    public int deletePurchaseByBid(@Param("buyId") Integer buyId);

    //根据id修改采购数量和实际采购数量
    public int updatePurchaseById(Purchase purchase);

    //添加入库单一:修改buy_list的is_in
    public int resetIsInById(@Param("buyId") Integer buyId);


}
