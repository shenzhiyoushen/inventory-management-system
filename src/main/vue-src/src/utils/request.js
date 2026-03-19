import axios from 'axios'

// 创建axios实例
const service = axios.create({
  // 原配置（跨域）
  // baseURL: 'http://localhost:8080/demo'

  // 新配置（同端口，去掉域名和端口，只保留context-path）
  baseURL: '/demo',
  timeout: 5000
})

// 请求拦截器：添加token
service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器：统一处理返回结果
service.interceptors.response.use(
  response => {
    const res = response.data
    // 401未登录，跳转到登录页
    if (res.code === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
      return Promise.reject(new Error(res.message || 'Error'))
    }
    // 非200都是错误
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    ElMessage.error(error.message || '服务器错误')
    return Promise.reject(error)
  }
)

export default service