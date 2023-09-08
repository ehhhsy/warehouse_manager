package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.ProductMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Product;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page queryAllPage(Page page, Product product) {
        //查询所有行数
        Integer count = productMapper.findProductRowCount(product);

        //分页查询商品
        List<Product> productList = productMapper.findProductPage(page, product);
        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(productList);
        return page;
    }

    /*
  将配置文件的file.access-path属性值注入给service的accessPath属性,
 * 其为上传的图片保存到数据库中的访问地址的目录路径/img/upload/;
 */
    @Value("${file.access-path}")
    private String accessPath;

    //添加商品的方法
    @Override
    public Result insertProduct(Product product) {
        //处理上传的图片的访问地址 -- /img/upload/图片名称
        product.setImgs(accessPath+product.getImgs());

        //添加商品
        int i = productMapper.insertProduct(product);

        if(i>0){
            return Result.ok("添加商品成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加商品失败！");
    }
    //更新商品状态
    @Override
    public Result updateStateByPid(Product product) {
        int i = productMapper.modifyProductState(product);
        if (i>0){
            return Result.ok("修改成功了！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败!?");
    }
    //删除商品 单个/批量
    @Override
    public Result deleteProductByPid(List<Integer> productIdList) {
        int i = productMapper.removeProductsByPid(productIdList);
        if (i>0){
            return Result.ok("删除商品成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除商品失败");
    }

    //修改商品的业务方法
    @Override
    public Result setProductById(Product product) {
        /*
          处理商品上传的图片的访问地址:
          如果product对象的imgs属性值没有以/img/upload/开始,说明商品的图片
          被修改了即上传了新的图片,那么product对象的imgs属性值只是图片的名称,
          则给图片名称前拼接/img/upload构成商品新上传的图片的访问地址;
         */
            if(!product.getImgs().startsWith(accessPath)){
                product.setImgs(accessPath+product.getImgs());
            }
            //根据商品id修改商品信息
            int i = productMapper.setProductByPid(product);
            if(i>0){
                return Result.ok("商品修改成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"商品修改失败！");
        }
}
