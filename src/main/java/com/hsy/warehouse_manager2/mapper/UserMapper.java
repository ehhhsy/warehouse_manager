package com.hsy.warehouse_manager2.mapper;
import java.util.*;
import com.hsy.warehouse_manager2.pojo.User;
import com.hsy.warehouse_manager2.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
    public interface UserMapper {

        //根据账号查询用户信息方法
        public User findUserById(@Param("userCode") String userCode);

        //查询用户的行数
         public Integer findUserRowCount(User user);
        //分页查询用户的方法
        public List<User> findUserByPage(@Param("page") Page page,@Param("user") User user);

        //添加用户的方法
        public int insertUser(User user);

        //根据用户id修改用户状态
        public int setStateByUid(@Param("id") Integer id,@Param("userState") String userState);

        //根据用户id修改用户is_delete状态
        public int setIsDeleteByUids(@Param("userIdList") List<Integer> userIdList);

        //根据用户id修改用户名称
        public int setUserNameByUser(User user);

        //根据用户id重置密码为123456
        public int resetPwdByUserId(@Param("userId") Integer userId,@Param("password") String password);
}
