package com.example.springbootuserbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

// 菜单实体类（JPA）
@Entity
@Table(name = "sys_menu")
@Data
public class SysMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private String menuName; // 菜单名称（如“商品管理”）
    private String parentId = "0"; // 父菜单ID（0为顶级）
    private String menuType; // 类型：M=菜单，B=按钮
    private String path; // 前端路由路径（如“/commodity/list”）
    private String perm; // 权限标识（如“commodity:list”）
    private Integer sort; // 排序号
    private Boolean status = true; // 启用状态
}