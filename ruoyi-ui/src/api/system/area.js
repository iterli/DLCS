import request from '@/utils/request'

// 查询区域列表
export function listArea(query) {
  return request({
    url: '/system/area/list',
    method: 'get',
    params: query
  })
}

// 查询区域详细
export function getArea(areaId) {
  return request({
    url: '/system/area/' + areaId,
    method: 'get'
  })
}

// 新增区域
export function addArea(data) {
  return request({
    url: '/system/area',
    method: 'post',
    data: data
  })
}

// 修改区域
export function updateArea(data) {
  return request({
    url: '/system/area',
    method: 'put',
    data: data
  })
}

// 删除区域
export function delArea(areaId) {
  return request({
    url: '/system/area/' + areaId,
    method: 'delete'
  })
}

// 导出区域
export function exportArea(query) {
  return request({
    url: '/system/area/export',
    method: 'get',
    params: query
  })
}

// 查询区域下拉树结构
export function treeselect() {
  return request({
    url: '/system/area/treeselect',
    method: 'get'
  })
}

// 查询区域列表（排除节点）
export function listAreaExcludeChild(areaId) {
  return request({
    url: '/system/area/list/exclude/' + areaId,
    method: 'get'
  })
}

// 区域状态修改
export function changeAreaStatus(areaId, status) {
  const data = {
    areaId,
    status
  }
  return request({
    url: '/system/area/changeStatus',
    method: 'put',
    data: data
  })
}
