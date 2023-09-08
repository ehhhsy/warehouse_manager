package com.hsy.warehouse_manager2.service;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Product;
import com.hsy.warehouse_manager2.pojo.Result;

import java.util.List;

public interface ProductService {

    //分页查询商品的业务方法
    public Page queryAllPage(Page page, Product product);


    //执行添加商品
    public Result insertProduct(Product product);

    //修改商品上下架状态
    public Result updateStateByPid(Product product);


    //删除商品 单个/批量
    public Result deleteProductByPid(List<Integer> productIdList);

    //修改商品的业务方法
    public Result setProductById(Product product);



}
