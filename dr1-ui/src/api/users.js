import request from './request'

export function getUsers() {
  return request.get('/system/users')
}

export function getUserPage(params) {
  return request.get('/system/users/page', { params })
}

export function createUser(data) {
  return request.post('/system/users', data)
}

export function updateUser(id, data) {
  return request.put(`/system/users/${id}`, data)
}

export function deleteUser(id) {
  return request.delete(`/system/users/${id}`)
}

