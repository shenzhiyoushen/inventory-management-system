<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">Java Demo 登录</h2>
      <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-btn">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { login } from '../api/user'

const router = useRouter()
const loginFormRef = ref(null)

// 登录表单
const loginForm = ref({
  username: '',
  password: ''
})

// 表单校验规则
const loginRules = ref({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

// 登录方法
const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      login(loginForm.value).then(res => {
        // 保存token
        localStorage.setItem('token', res.data)
        ElMessage.success('登录成功')
        // 跳转到用户列表页
        router.push('/user/list')
      }).catch(err => {
        console.error(err)
      })
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
}
</style>