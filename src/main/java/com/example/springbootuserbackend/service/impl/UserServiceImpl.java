
// UserServiceImpl.java
package com.example.springbootuserbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootuserbackend.common.BusinessException;
import com.example.springbootuserbackend.dto.LoginDTO;
import com.example.springbootuserbackend.entity.User;
import com.example.springbootuserbackend.mapper.UserMapper;
import com.example.springbootuserbackend.service.UserService;
import com.example.springbootuserbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginDTO loginDTO) {
        // 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = this.baseMapper.selectOne(queryWrapper);

        // 验证用户是否存在+密码是否正确（演示用明文，实际需加密）
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }
        if (!loginDTO.getPassword().equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成JWT token
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public Page<User> getUserPage(Page<User> page, String username, String phone) {
        // 构建条件查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            queryWrapper.like(User::getUsername, username);
        }
        if (phone != null && !phone.isEmpty()) {
            queryWrapper.like(User::getPhone, phone);
        }
        // 按创建时间降序
        queryWrapper.orderByDesc(User::getCreateTime);
        
        // 分页查询
        return this.baseMapper.selectPage(page, queryWrapper);
    }
}