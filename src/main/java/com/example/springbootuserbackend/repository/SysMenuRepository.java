package com.example.springbootuserbackend.repository;

import com.example.springbootuserbackend.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 菜单数据访问层（JPA自动实现CRUD）
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {

    /**
     * 查询所有启用的菜单（核心查询方法）
     * @param status 菜单状态（true=启用）
     * @return 启用的菜单列表
     */
    List<SysMenu> findByStatus(Boolean status);

    /**
     * 按父菜单ID查询子菜单（用于前端层级渲染）
     * @param parentId 父菜单ID（顶级菜单parentId为"0"）
     * @param status 菜单状态
     * @return 子菜单列表
     */
    List<SysMenu> findByParentIdAndStatusOrderBySortAsc(String parentId, Boolean status);

    /**
     * 按菜单名称模糊查询（可选，用于菜单搜索）
     * @param menuName 菜单名称关键词
     * @param status 菜单状态
     * @return 匹配的菜单列表
     */
    List<SysMenu> findByMenuNameContainingAndStatus(String menuName, Boolean status);
}