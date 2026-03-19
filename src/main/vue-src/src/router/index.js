import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import UserList from '../views/UserList.vue'

// 路由守卫：判断是否登录
const requireAuth = (to, from, next) => {
  const token = localStorage.getItem('token')
  if (token) {
    next()
  } else {
    next('/login')
  }
}

const routes = [
  {
    path: '/',
    redirect: '/user/list'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/user/list',
    name: 'UserList',
    component: UserList,
    beforeEnter: requireAuth
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router