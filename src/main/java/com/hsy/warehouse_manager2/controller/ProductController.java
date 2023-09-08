package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.pojo.*;
import com.hsy.warehouse_manager2.service.*;
import com.hsy.warehouse_manager2.until.TokenUtils;
import io.netty.util.internal.ResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import com.hsy.warehouse_manager2.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * /product/store-list  查询所有仓库的  给搜索商品仓库下拉框组装数据的
     */
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }


    /**
     * /product/brand-list -- 查询所有品牌 -- 给搜索商品品牌下拉框组装数据的
     */
    @RequestMapping("/brand-list")
    public Result brandList(){
        //执行业务
        List<Brand> brandList = brandService.queryAllBrand();
        return Result.ok(brandList);
    }

    /**
     * 分页查询商品的url接口 /product/product-page-list
     */
    @RequestMapping("/product-page-list")
    public Result productListPage(Page page, Product product){
        //执行业务
        page = productService.queryAllPage(page, product);
        return Result.ok(page);
    }

    /**
     * /product/category-tree -- 查询所有分类树 -- 给添加商品提供的
     */
    @RequestMapping("/category-tree")
    public Result loadTypeTree(){
        //执行业务
        List<ProductType> typeList = productTypeService.productTypeTree();
        return Result.ok(typeList);
    }

    /**
     * /product/supply-list -- 查询所有供应商 -- 给添加商品提供的
     */
    @RequestMapping("/supply-list")
    public Result loadSupplier(){
        List<Supply> supplyList = supplyService.queryAllSupply();
        return Result.ok(supplyList);
    }

    /**
     * /product/place-list -- 查询所有产地 -- 给添加商品提供的
     */
    @RequestMapping("/place-list")
    public Result loadPlace(){
        //执行业务
        List<Place> placeList = placeService.queryAllPlace();
        return Result.ok(placeList);
    }

    /**
     * /product/unit-list -- 查询所有单位 -- 给添加商品提供的
     */
    @RequestMapping("/unit-list")
    public Result loadUnit(){
        //执行业务
        List<Unit> unitList = unitService.findAllUnit();
        return Result.ok(unitList);
    }

    /**
     *  发送/product/img-upload -- 上传图片 -- 给添加商品提供的
     *  file.transferTo(上传的文件保持到磁盘文件的file对象); --实现文件的上传
     */
    //将配置文件中file.uplad-path属性值注入给控制器的成员属uploadPath,图片上传的位置classpath:static/img/upload
    @Value("${file.upload-path}")
    private String uploadPath;
    @CrossOrigin  //表示此接口/product/img-upload 允许被跨域请求
    @RequestMapping("/img-upload")
    public Result loadImg(MultipartFile file){
        try {
            //拿到图片上传到的目录路径的File对象 （classpath:static/img/upload）
//            因为图片上传到的目录路径是个类路径(resource 下的路径/cLasses 下的路径)
//            不能直接将路径封装到File对象,因为带有(classPath:) 可以使用类路径资源解析工具类ResourceUtil.getFile方法来解析路径
//            这个可以拿到
            File uploadDirFile = ResourceUtils.getFile(uploadPath);

            //拿到图片上传到目录的磁盘路径
            String path = uploadDirFile.getAbsolutePath();
            //拿到上传图片的名称
            String originalFilename = file.getOriginalFilename();
            //拿到上传的文件保存到的磁盘文件的路径
            String uploadFilePath = path + "\\" + originalFilename;
            //上传图片
            file.transferTo(new File(uploadFilePath));
            return Result.ok("图片上传成功>");
        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS,"上传失败!??");
        }
    }

    /**
     * /product/product-add -- 添加商品
     */
    @RequestMapping("product-add")
    public Result addProduct(@RequestBody Product product, @RequestHeader String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        product.setCreateBy(currentUser.getUserId());

        //执行业务
        Result result = productService.insertProduct(product);
        return result;
    }

    /**
     * /product/state-change 商品上下架状态
     */
    @RequestMapping("/state-change")
    public Result uploadProduct(@RequestBody Product product){
        //执行业务
        Result result = productService.updateStateByPid(product);
        return result;
    }

    /**
     * /product/product-delete/{productId} -- 根据商品id删除单个商品
     *
     */
    @RequestMapping("/product-delete/{productId}")
    public Result deleteOneProduct(@PathVariable Integer productId){
        List<Integer> list = Arrays.asList(productId);
        //执行业务
        Result result = productService.deleteProductByPid(list);
        return result;
    }


    /**
     * /product/product-list-delete --- 批量删除商品
     */
    @RequestMapping("/product-list-delete")
    public Result deleteProducts(@RequestBody List<Integer> productIdList){
        //执行业务
        Result result = productService.deleteProductByPid(productIdList);
        return result;
    }
    /**
     * /product/product-update 修改商品 根据商品id修改其他
     */
    @RequestMapping("/product-update")
    public Result setProductById(@RequestBody Product product,@RequestHeader String token){
        //拿取当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        product.setCreateBy(currentUser.getUserId());

        //执行业务
        Result result = productService.setProductById(product);
        return result;
    }

    /**
     * /product/exportTable 导出商品列表
     */
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,Product product){
        //分页查询
        page = productService.queryAllPage(page, product);
        List<?> resultList = page.getResultList();
        return Result.ok(resultList);
    }
}
