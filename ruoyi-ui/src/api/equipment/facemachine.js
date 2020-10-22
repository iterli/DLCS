import request from '@/utils/request'

// 查询人脸设备信息列表
export function listFacemachine(query) {
  return request({
    url: '/equipment/facemachine/list',
    method: 'get',
    params: query
  })
}
// 查询人脸设备信息详细
export function getFacemachine(faceId) {
  return request({
    url: '/equipment/facemachine/' + faceId,
    method: 'get'
  })
}

// 新增人脸设备信息
export function addFacemachine(data) {
  return request({
    url: '/equipment/facemachine',
    method: 'post',
    data: data
  })
}

// 修改人脸设备信息
export function updateFacemachine(data) {
  return request({
    url: '/equipment/facemachine',
    method: 'put',
    data: data
  })
}

// 删除人脸设备信息
export function delFacemachine(faceId) {
  return request({
    url: '/equipment/facemachine/' + faceId,
    method: 'delete'
  })
}

// 导出人脸设备信息
export function exportFacemachine(query) {
  return request({
    url: '/equipment/facemachine/export',
    method: 'get',
    params: query
  })
}
export function getFaceInforation(faceIds){
    return request({
      url: '/equipment/facemachine/getInforation/' + faceIds,
      method: 'post'
    })
}
