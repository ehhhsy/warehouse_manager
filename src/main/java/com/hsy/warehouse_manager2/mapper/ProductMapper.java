package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import com.hsy.warehouse_manager2.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    //查询商品行数
    public Integer findProductRowCount(Product product);

    //分页查询商品的方法,(条件/全查都可以用)
    public List<Product> findProductPage(@Param("page") Page page, @Param("product") Product product);


    //添加商品的方法
    public int insertProduct( Product product);

    //修改商品上下架状态
    public int modifyProductState(Product product);

    //根据商品id删除商品，批量删除/单个删除
    public int removeProductsByPid(List<Integer> productIdList);

    //根据商品id修改商品
    public int setProductByPid(Product product);


    //确认入库其二：根据商品id修改商品库存
    public int setInventById(@Param("productId") Integer productId,@Param("invent") Integer invent);


    //根据商品id查询商品库存
    public int findInventById(@Param("productId") Integer productId);
}
