package com.example.springbootuserbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootuserbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 条件查询用户（MyBatis-Plus已封装基础CRUD，这里演示自定义条件）
    List<User> selectUserByCondition(@Param("username") String username, @Param("phone") String phone);
}