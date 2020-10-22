package com.ruoyi.project.equipment.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.equipment.domain.EquFacemachine;
import com.ruoyi.project.equipment.mapper.EquFacemachineMapper;
import com.ruoyi.project.equipment.service.IEquFacemachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人脸设备信息Service业务层处理
 *
 * @author ruoyi
 * @date 2020-10-19
 */
@Service
public class EquFacemachineServiceImpl implements IEquFacemachineService
{
    @Autowired
    private EquFacemachineMapper equFacemachineMapper;

    /**
     * 查询人脸设备信息
     *
     * @param faceId 人脸设备信息ID
     * @return 人脸设备信息
     */
    @Override
    public EquFacemachine selectEquFacemachineById(Long faceId)
    {
        return equFacemachineMapper.selectEquFacemachineById(faceId);
    }

    /**
     * 查询人脸设备信息列表
     *
     * @param equFacemachine 人脸设备信息
     * @return 人脸设备信息
     */
    @Override
    public List<EquFacemachine> selectEquFacemachineList(EquFacemachine equFacemachine)
    {
        return equFacemachineMapper.selectEquFacemachineList(equFacemachine);
    }

    /**
     * 新增人脸设备信息
     *
     * @param equFacemachine 人脸设备信息
     * @return 结果
     */
    @Override
    public int insertEquFacemachine(EquFacemachine equFacemachine)
    {
        equFacemachine.setCreateTime(DateUtils.getNowDate());
        return equFacemachineMapper.insertEquFacemachine(equFacemachine);
    }

    /**
     * 修改人脸设备信息
     *
     * @param equFacemachine 人脸设备信息
     * @return 结果
     */
    @Override
    public int updateEquFacemachine(EquFacemachine equFacemachine)
    {
        equFacemachine.setUpdateTime(DateUtils.getNowDate());
        return equFacemachineMapper.updateEquFacemachine(equFacemachine);
    }

    /**
     * 批量删除人脸设备信息
     *
     * @param faceIds 需要删除的人脸设备信息ID
     * @return 结果
     */
    @Override
    public int deleteEquFacemachineByIds(Long[] faceIds)
    {
        return equFacemachineMapper.deleteEquFacemachineByIds(faceIds);
    }

    /**
     * 删除人脸设备信息信息
     *
     * @param faceId 人脸设备信息ID
     * @return 结果
     */
    @Override
    public int deleteEquFacemachineById(Long faceId)
    {
        return equFacemachineMapper.deleteEquFacemachineById(faceId);
    }
}
