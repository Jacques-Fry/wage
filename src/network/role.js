import {
    request
} from "./request"

export function searchAllRole() {
    return request({
        url: '/role/searchAllRole'
    })
}