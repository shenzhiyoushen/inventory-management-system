// UserService.java
package com.example.springbootuserbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootuserbackend.common.BusinessException;
import com.example.springbootuserbackend.dto.LoginDTO;
import com.example.springbootuserbackend.entity.User;
import com.example.springbootuserbackend.mapper.UserMapper;
import com.example.springbootuserbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service接口
 */
public interface UserService extends IService<User> {
    // 登录
    String login(LoginDTO loginDTO);

    // 分页+条件查询用户
    Page<User> getUserPage(Page<User> page, String username, String phone);
}
