import {
  request
} from "./request"

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
      roleId: user.roleId,
      type: user.type
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