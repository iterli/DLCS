package com.ruoyi.project.equipment.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.equipment.domain.EquEntrance;
import com.ruoyi.project.equipment.mapper.EquEntranceMapper;
import com.ruoyi.project.equipment.service.IEquEntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门禁设备信息Service业务层处理
 *
 * @author ruoyi
 * @date 2020-10-19
 */
@Service
public class EquEntranceServiceImpl implements IEquEntranceService
{
    @Autowired
    private EquEntranceMapper equEntranceMapper;

    /**
     * 查询门禁设备信息
     *
     * @param enId 门禁设备信息ID
     * @return 门禁设备信息
     */
    @Override
    public EquEntrance selectEquEntranceById(Long enId)
    {
        return equEntranceMapper.selectEquEntranceById(enId);
    }

    /**
     * 查询门禁设备信息列表
     *
     * @param equEntrance 门禁设备信息
     * @return 门禁设备信息
     */
    @Override
    public List<EquEntrance> selectEquEntranceList(EquEntrance equEntrance)
    {
        return equEntranceMapper.selectEquEntranceList(equEntrance);
    }

    /**
     * 新增门禁设备信息
     *
     * @param equEntrance 门禁设备信息
     * @return 结果
     */
    @Override
    public int insertEquEntrance(EquEntrance equEntrance)
    {
        equEntrance.setCreateTime(DateUtils.getNowDate());
        return equEntranceMapper.insertEquEntrance(equEntrance);
    }

    /**
     * 修改门禁设备信息
     *
     * @param equEntrance 门禁设备信息
     * @return 结果
     */
    @Override
    public int updateEquEntrance(EquEntrance equEntrance)
    {
        equEntrance.setUpdateTime(DateUtils.getNowDate());
        return equEntranceMapper.updateEquEntrance(equEntrance);
    }

    /**
     * 批量删除门禁设备信息
     *
     * @param enIds 需要删除的门禁设备信息ID
     * @return 结果
     */
    @Override
    public int deleteEquEntranceByIds(Long[] enIds)
    {
        return equEntranceMapper.deleteEquEntranceByIds(enIds);
    }

    /**
     * 删除门禁设备信息信息
     *
     * @param enId 门禁设备信息ID
     * @return 结果
     */
    @Override
    public int deleteEquEntranceById(Long enId)
    {
        return equEntranceMapper.deleteEquEntranceById(enId);
    }
}
