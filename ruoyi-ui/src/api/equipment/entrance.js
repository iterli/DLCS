import request from '@/utils/request'

// 查询门禁设备信息列表
export function listEntrance(query) {
  return request({
    url: '/equipment/entrance/list',
    method: 'get',
    params: query
  })
}


// 查询门禁设备信息详细
export function getEntrance(enId) {
  return request({
    url: '/equipment/entrance/' + enId,
    method: 'get'
  })
}

// 新增门禁设备信息
export function addEntrance(data) {
  return request({
    url: '/equipment/entrance',
    method: 'post',
    data: data
  })
}

// 修改门禁设备信息
export function updateEntrance(data) {
  return request({
    url: '/equipment/entrance',
    method: 'put',
    data: data
  })
}

// 删除门禁设备信息
export function delEntrance(enId) {
  return request({
    url: '/equipment/entrance/' + enId,
    method: 'delete'
  })
}

// 导出门禁设备信息
export function exportEntrance(query) {
  return request({
    url: '/equipment/entrance/export',
    method: 'get',
    params: query
  })
}
