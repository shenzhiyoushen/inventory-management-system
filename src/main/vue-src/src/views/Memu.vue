<template>
    <el-menu :default-active="activePath" router>
      <!-- 基础数据 -->
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>基础数据
        </template>
        <el-menu-item index="/category/list">商品分类</el-menu-item>
        <el-menu-item index="/commodity/list">商品管理</el-menu-item>
        <el-menu-item index="/supplier/list">供应商管理</el-menu-item>
      </el-submenu>
      
      <!-- 库存管理 -->
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-location"></i>库存管理
        </template>
        <el-menu-item index="/stock/list">库存查询</el-menu-item>
        <el-menu-item index="/stock/check">库存盘点</el-menu-item>
        <el-menu-item index="/stock/warning">库存预警</el-menu-item>
      </el-submenu>
      
      <!-- 其他菜单组同理，省略 -->
    </el-menu>
  </template>
  
  <script>
  export default {
    data() {
      return {
        activePath: window.location.pathname // 默认选中当前路由
      }
    }
  }
  </script>