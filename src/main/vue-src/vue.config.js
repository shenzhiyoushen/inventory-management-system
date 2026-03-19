// src/main/vue-src/vue.config.js
const { defineConfig } = require('@vue/cli-service')
module.exports = {
    // 打包输出目录（指向Spring Boot的static目录，相对路径需匹配你的目录层级）
    outputDir: '../../../src/main/resources/static',
    // 公共路径（关键，确保打包后资源引用路径正确）
    publicPath: '/demo/',
    productionSourceMap: false
  }