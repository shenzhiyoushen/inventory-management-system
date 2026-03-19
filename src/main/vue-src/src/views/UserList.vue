<!-- src/main/vue-src/src/views/UserList.vue -->
<template>
  <div class="user-container">
    <el-page-header content="用户管理"></el-page-header>
    
    <!-- 查询条件 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline @submit.prevent="getUserList">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="searchForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getUserList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="openAddDialog">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 用户列表 -->
    <el-card>
      <el-table :data="userList" border stripe>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="searchForm.current"
        v-model:page-size="searchForm.size"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="getUserList"
        @current-change="getUserList"
        style="margin-top: 20px; text-align: right"
      ></el-pagination>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="userForm" :rules="userRules" ref="userFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="isAdd">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
// 核心修复：确保所有导入、变量、函数定义完整且无语法错误
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
// 注意：这里的API路径要和你的实际封装一致
import { 
  getUserPage, 
  addUser, 
  updateUser, 
  deleteUser as deleteUserApi, 
  getUserById 
} from '../api/user'

// ========== 基础变量定义 ==========
// 查询表单
const searchForm = ref({
  current: 1,  // 当前页
  size: 10,    // 每页条数
  username: '',
  phone: ''
})

// 用户列表
const userList = ref([])
// 总条数
const total = ref(0)

// 弹窗相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isAdd = ref(true)
const userFormRef = ref(null)

// 用户表单
const userForm = ref({
  id: '',
  username: '',
  password: '',
  phone: '',
  email: ''
})

// 表单校验规则
const userRules = ref({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }]
})

// ========== 方法定义 ==========
// 获取用户列表
const getUserList = () => {
  getUserPage(searchForm.value).then(res => {
    userList.value = res.data.records
    total.value = res.data.total
  }).catch(err => {
    console.error('获取用户列表失败：', err)
  })
}

// 重置查询条件
const resetSearch = () => {
  searchForm.value = {
    current: 1,
    size: 10,
    username: '',
    phone: ''
  }
  getUserList()
}

// 打开新增弹窗
const openAddDialog = () => {
  isAdd.value = true
  dialogTitle.value = '新增用户'
  // 重置表单
  userForm.value = {
    id: '',
    username: '',
    password: '',
    phone: '',
    email: ''
  }
  dialogVisible.value = true
}

// 打开编辑弹窗
const openEditDialog = (row) => {
  isAdd.value = false
  dialogTitle.value = '编辑用户'
  // 查询用户详情
  getUserById(row.id).then(res => {
    userForm.value = res.data
    dialogVisible.value = true
  }).catch(err => {
    console.error('获取用户详情失败：', err)
  })
}

// 保存用户（新增/编辑）
const saveUser = () => {
  userFormRef.value.validate((valid) => {
    if (valid) {
      const request = isAdd.value ? addUser(userForm.value) : updateUser(userForm.value)
      request.then(() => {
        ElMessage.success(isAdd.value ? '新增成功' : '编辑成功')
        dialogVisible.value = false
        getUserList()
      }).catch(err => {
        console.error('保存用户失败：', err)
        ElMessage.error('操作失败，请重试')
      })
    }
  })
}

// 删除用户（重命名避免关键字冲突）
const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteUserApi(id).then(() => {
      ElMessage.success('删除成功')
      getUserList()
    }).catch(err => {
      console.error('删除用户失败：', err)
      ElMessage.error('删除失败，请重试')
    })
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// ========== 生命周期 ==========
// 页面加载时获取用户列表
onMounted(() => {
  getUserList()
})
</script>

<style scoped>
.user-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}
</style>