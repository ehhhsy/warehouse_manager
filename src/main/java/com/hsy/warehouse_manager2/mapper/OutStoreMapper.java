package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.OutStore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import com.hsy.warehouse_manager2.page.Page;
@Service
public interface OutStoreMapper {

    //添加出库单out_store的方法
    public int insertOutStore(OutStore outStore);

    //查询出库单总行数的方法
    public int outStoreCount(OutStore outStore);

    //分页查询出库单的方法
    public List<OutStore> outStorePage(@Param("page") Page page,
                                       @Param("outStore") OutStore outStore);

    //根据id修改出库单状态改为1已出库
    public int setIsOutById(@Param("outStoreId") Integer outStoreId);
}
