import { request } from "./request"

export function login(username, password) {
  return request({
    url: '/user/login',
    params: {
      username,
      password
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