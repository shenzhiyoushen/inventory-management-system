package com.example.springbootuserbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootuserbackend.common.Result;
import com.example.springbootuserbackend.dto.LoginDTO;
import com.example.springbootuserbackend.dto.UserQuery;
import com.example.springbootuserbackend.entity.User;
import com.example.springbootuserbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理接口", description = "包含登录、CRUD、分页查询等接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "用户名+密码登录，返回JWT token")
    public Result<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return Result.success(token);
    }

    /**
     * 分页+条件查询用户
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询用户", description = "支持用户名、手机号模糊查询，分页返回")
    public Result<Page<User>> getUserPage(@RequestBody UserQuery userQuery) {
        Page<User> page = userService.getUserPage(userQuery, userQuery.getUsername(), userQuery.getPhone());
        return Result.success(page);
    }

    /**
     * 新增用户
     */
    @PostMapping
    @Operation(summary = "新增用户", description = "添加新用户")
    public Result<?> addUser(@RequestBody User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return Result.success();
    }

    /**
     * 修改用户
     */
    @PutMapping
    @Operation(summary = "修改用户", description = "根据ID修改用户信息")
    public Result<?> updateUser(@RequestBody User user) {
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    public Result<?> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询单个用户", description = "根据ID查询用户详情")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }
}