package com.example.springbootuserbackend.service;

import com.example.springbootuserbackend.entity.SysMenu;
import com.example.springbootuserbackend.repository.SysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 菜单业务层
 */
@Service
public class MenuService {

    @Autowired
    private SysMenuRepository menuRepository;

    /**
     * 首次启动系统时，初始化默认菜单（仅执行一次）
     */
    @PostConstruct
    public void initDefaultMenu() {
        // 先检查是否已有菜单，避免重复初始化
        long menuCount = menuRepository.count();
        if (menuCount > 0) {
            return;
        }

        // 1. 新增顶级菜单组（基础数据）
        SysMenu baseDataMenu = new SysMenu();
        baseDataMenu.setMenuName("基础数据");
        baseDataMenu.setParentId("0");
        baseDataMenu.setMenuType("M"); // M=菜单
        baseDataMenu.setPath("/base-data");
        baseDataMenu.setPerm("base:data");
        baseDataMenu.setSort(1);
        baseDataMenu.setStatus(true);
        menuRepository.save(baseDataMenu);

        // 2. 新增基础数据的子菜单（商品管理）
        SysMenu commodityMenu = new SysMenu();
        commodityMenu.setMenuName("商品管理");
        commodityMenu.setParentId(baseDataMenu.getId().toString()); // 关联父菜单
        commodityMenu.setMenuType("M");
        commodityMenu.setPath("/commodity/list");
        commodityMenu.setPerm("commodity:list");
        commodityMenu.setSort(1);
        commodityMenu.setStatus(true);
        menuRepository.save(commodityMenu);

        // 3. 继续初始化其他核心菜单（库存管理、销售管理等，可按需补充）
        // 库存管理顶级菜单
        SysMenu stockMenu = new SysMenu();
        stockMenu.setMenuName("库存管理");
        stockMenu.setParentId("0");
        stockMenu.setMenuType("M");
        stockMenu.setPath("/stock");
        stockMenu.setPerm("stock:manage");
        stockMenu.setSort(2);
        stockMenu.setStatus(true);
        menuRepository.save(stockMenu);

        // 库存查询子菜单
        SysMenu stockQueryMenu = new SysMenu();
        stockQueryMenu.setMenuName("库存查询");
        stockQueryMenu.setParentId(stockMenu.getId().toString());
        stockQueryMenu.setMenuType("M");
        stockQueryMenu.setPath("/stock/list");
        stockQueryMenu.setPerm("stock:list");
        stockQueryMenu.setSort(1);
        stockQueryMenu.setStatus(true);
        menuRepository.save(stockQueryMenu);

        // 其他菜单（销售开单、数据备份等）可按相同逻辑补充
    }

    /**
     * 获取当前用户的菜单列表（简化版：返回所有启用的菜单）
     */
    public List<SysMenu> getUserMenuList() {
        return menuRepository.findByStatus(true);
    }

    /**
     * 按父菜单ID获取子菜单（用于前端层级渲染）
     */
    public List<SysMenu> getSubMenuList(String parentId) {
        return menuRepository.findByParentIdAndStatusOrderBySortAsc(parentId, true);
    }
}