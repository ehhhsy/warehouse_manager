package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.ProductType;
import com.hsy.warehouse_manager2.pojo.Result;

import java.util.*;
public interface ProductTypeService {

    //查询商品分类树，用于添加商品
    public List<ProductType> productTypeTree();

    //根据分类id查询商品分类名称是否存在，
    public Result queryTypeCode(String typeCode);

    //添加商品分类
    public Result saveProductType(ProductType productType);

    //删除商品分类
    public Result removeProductType(Integer typeId);

    //修改商品分类 根据`type_id`修改`TypeName`，`TypeDesc`
    public Result modifyProductType(ProductType productType);
}
