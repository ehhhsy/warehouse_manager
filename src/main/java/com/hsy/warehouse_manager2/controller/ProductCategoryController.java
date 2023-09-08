package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.pojo.ProductType;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.ProductService;
import com.hsy.warehouse_manager2.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 查询商品分类树木
     * @return
     */
    @RequestMapping("/product-category-tree")
    public Result showAllProduct(){
        List<ProductType> productTypeList = productTypeService.productTypeTree();
        return Result.ok(productTypeList);
    }

    /**
     * /productCategory/verify-type-code?typeCode=#{typeCode} 根据分类编码查询商品分类是否存在
     * 直接接收
     */
    @RequestMapping("/verify-type-code")
    public Result selectTypeCode(String typeCode){
        //执行业务
        Result result = productTypeService.queryTypeCode(typeCode);
        return result;
    }
    /**
     * productCategory/type-add 添加商品分类
     */
    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType){
        //执行业务
        Result result = productTypeService.saveProductType(productType);

        return result;
    }

    /**
     * /productCategory/type-delete/{typeId} 根据typeId删除商品分类以及子类
     */
    @RequestMapping("/type-delete/{typeId}")
    public Result deleteProductTypeByTid(@PathVariable Integer typeId){
        //执行业务
        Result result = productTypeService.removeProductType(typeId);

        return result;
    }

    /**
     * `/productCategory/type-update`  根据`type_id`修改`TypeName`，`TypeDesc`
     */
    @RequestMapping("/type-update")
    public Result modify(@RequestBody ProductType productType){
        //执行业务
        Result result = productTypeService.modifyProductType(productType);
        return result;
    }

}
