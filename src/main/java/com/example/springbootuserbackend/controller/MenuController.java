package com.example.springbootuserbackend.controller;

import com.example.springbootuserbackend.entity.SysMenu;
import com.example.springbootuserbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 菜单接口层（最终唯一版本）
 * 前端所有菜单相关请求都走这个控制器，路径统一为 /api/menu
 */
@RestController
@RequestMapping("/api/menu") // 加/api前缀，区分接口和页面路由，更规范
public class MenuController {

    @Autowired
    private MenuService menuService; // 注入服务层，而非直接注入Repository

    /**
     * 【核心】获取所有启用的菜单（用于前端侧边栏整体渲染）
     * 前端调用示例：GET http://localhost:8080/api/menu/list
     * 返回：所有菜单列表（包含顶级和子菜单）
     */
    @GetMapping("/list")
    public List<SysMenu> getMenuList() {
        return menuService.getUserMenuList();
    }

    /**
     * 按父菜单ID获取子菜单（用于前端分级渲染，可选）
     * 前端调用示例：GET http://localhost:8080/api/menu/sub/0（获取顶级菜单）
     * @param parentId 父菜单ID（顶级菜单传"0"）
     * 返回：指定父菜单下的所有子菜单
     */
    @GetMapping("/sub/{parentId}")
    public List<SysMenu> getSubMenuList(@PathVariable String parentId) {
        return menuService.getSubMenuList(parentId);
    }
}