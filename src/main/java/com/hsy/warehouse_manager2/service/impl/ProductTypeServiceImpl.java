package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.ProductTypeMapper;
import com.hsy.warehouse_manager2.pojo.ProductType;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.ProductTypeServiceImpl")
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //查询商品分类树，用于添加商品
    @Cacheable(key = "'all:TypeTree'")
    @Override
    public List<ProductType> productTypeTree() {
            //查询所有商品分类
        List<ProductType> productTypeList = productTypeMapper.findAllProductType();
        //将所有商品转为商品分类树
        List<ProductType> typeTree = allTypeToTypeTree(productTypeList, 0);
        return typeTree;
    }

    ///根据分类编码查询商品是否存在
    @Override
    public Result queryTypeCode(String typeCode) {
        //根据分类编码查询商品分类
        ProductType productType = productTypeMapper.findTypeByCode(typeCode);

        return Result.ok(productType==null);

    }
    //添加商品分类，需要清缓存
    @Override
    public Result saveProductType(ProductType productType) {
        int i = productTypeMapper.insertProductType(productType);
        if (i>0){
            //清缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.ProductTypeServiceImpl::all:TypeTree");

            return Result.ok("添加成功.");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
    }

    //删除商品分类,需要清缓存
    @Override
    public Result removeProductType(Integer typeId) {
       //执行mapper
        int i = productTypeMapper.deleteProductType(typeId);
        if (i>0){
            //清除缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.ProductTypeServiceImpl::all:TypeTree");
            return Result.ok("删除成功?");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败！?");
    }

    //修改商品分类 根据`type_id`修改`TypeName`，`TypeDesc`
    @Override
    public Result modifyProductType(ProductType productType) {
        //执行mapper
        int i = productTypeMapper.updateTypeById(productType);
        if (i>0){
            //清缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.ProductTypeServiceImpl::all:TypeTree");
            return Result.ok("修改成功>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败!?");
    }

    /**
     *将所有商品分类转成商品分类树的递归算法
     * @param productTypeList 要转化的数组list
     * @param Pid  :parent_id 父id
     * @return
     */
    private List<ProductType> allTypeToTypeTree(List<ProductType> productTypeList,Integer Pid){
        //拿到所有一级分类
        ArrayList<ProductType> firstLevelList = new ArrayList<>();

        for (ProductType productType : productTypeList) {
            if (productType.getParentId().equals(Pid)){
                firstLevelList.add(productType);
            }
        }
        //拿到每个二级分类
        for (ProductType productType : firstLevelList) {
            List<ProductType> secLevelList = allTypeToTypeTree(productTypeList, productType.getTypeId());
            productType.setChildProductCategory(secLevelList);
        }
        return firstLevelList;
    }
}
