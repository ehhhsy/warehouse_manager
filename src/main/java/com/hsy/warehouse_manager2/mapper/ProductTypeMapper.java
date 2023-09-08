package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Product;
import com.hsy.warehouse_manager2.pojo.ProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ProductTypeMapper {

    //查询所有商品分类树
    public List<ProductType> findAllProductType();

    //根据分类编码查询商品分类的方法
    public ProductType findTypeByCode(@Param("typeCode") String typeCode);

    //添加商品分类的方法
    public int insertProductType(ProductType productType);


    //根据分类id删除分类及其所有子级分类的方法
    public int deleteProductType(@Param("typeId") Integer typeId);

    //根据分类id修改分类的方法
    public int updateTypeById(ProductType productType);
}
