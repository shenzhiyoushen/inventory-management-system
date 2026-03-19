package com.example.springbootuserbackend.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootuserbackend.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询条件（分页+条件）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户查询参数")
public class UserQuery extends Page<User> {
    @Schema(description = "用户名模糊查询", example = "admin")
    private String username;

    @Schema(description = "手机号模糊查询", example = "138")
    private String phone;
}