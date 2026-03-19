import request from '../utils/request'

// 登录
export const login = (data) => {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 分页查询用户
export const getUserPage = (data) => {
  return request({
    url: '/user/page',
    method: 'post',
    data
  })
}

// 新增用户
export const addUser = (data) => {
  return request({
    url: '/user',
    method: 'post',
    data
  })
}

// 修改用户
export const updateUser = (data) => {
  return request({
    url: '/user',
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return request({
    url: `/user/${id}`,
    method: 'delete'
  })
}

// 根据ID查询用户
export const getUserById = (id) => {
  return request({
    url: `/user/${id}`,
    method: 'get'
  })
}