package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.InStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InStoreMapper {
    //添加入库单二:添加入库单in_store
    public int insertInStore(InStore inStore);

    //查询入库单总行数的方法
    public int selectInStoreCount(InStore inStore);

    //分页查询入库单的方法
    public List<InStore> selectInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);

    //确认入库其一：根据id修改入库单状态
    public int setIsInById(Integer inStoreId);

}
