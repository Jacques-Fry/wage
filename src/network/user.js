import {
  request
} from "./request"

export function searchUser(page, size, username, tel, roleId) {
  return request({
    method: 'post',
    url: '/user/search/' + page + '/' + size,
    data: {
      username, tel, roleId
    }
  })
}

export function login(username, password) {
  return request({
    url: '/user/login',
    params: {
      username,
      password
    }

  })
}

export function createUser(user) {
  return request({
    method: 'post',
    url: '/user/createUser',
    data: {
      username: user.username,
      tel: user.tel,
      password: user.password,
      type: user.type
    }
  })
}

export function updUser(user) {
  return request({
    method: 'put',
    url: '/user/updUser/' + user.id,
    data: {
      username: user.username,
      tel: user.tel,
      password: user.password
    }
  })
}

export function detail() {
  return request({
    url: '/user/detail'
  })
}

export function detailById(id) {
  return request({
    url: '/user/detail/' + id
  })
}

export function delUser(id) {
  return request({
    method: 'delete',
    url: '/user/' + id
  })
}

export function freeze(id) {
  return request({
    method: 'put',
    url: '/user/freeze/' + id
  })
}

export function unfreeze(id) {
  return request({
    method: 'put',
    url: '/user/unfreeze/' + id
  })
}

export function adminDemotionUser(id) {
  return request({
    method: 'put',
    url: '/role/adminDemotionUser/' + id
  })
}

export function userUpgradeAdmin(id) {
  return request({
    method: 'put',
    url: '/role/userUpgradeAdmin/' + id
  })
}

