package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.Supply;
import java.util.List;
public interface SupplyService {

    // 查询所有供应商 -- 给添加商品提供的
    public List<Supply> queryAllSupply();
}
