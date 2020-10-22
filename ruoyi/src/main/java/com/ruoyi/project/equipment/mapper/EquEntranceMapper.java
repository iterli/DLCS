package com.ruoyi.project.equipment.mapper;

import com.ruoyi.project.equipment.domain.EquEntrance;

import java.util.List;

/**
 * 门禁设备信息Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-19
 */
public interface EquEntranceMapper
{
    /**
     * 查询门禁设备信息
     *
     * @param enId 门禁设备信息ID
     * @return 门禁设备信息
     */
    public EquEntrance selectEquEntranceById(Long enId);

    /**
     * 查询门禁设备信息列表
     *
     * @param equEntrance 门禁设备信息
     * @return 门禁设备信息集合
     */
    public List<EquEntrance> selectEquEntranceList(EquEntrance equEntrance);

    /**
     * 新增门禁设备信息
     *
     * @param equEntrance 门禁设备信息
     * @return 结果
     */
    public int insertEquEntrance(EquEntrance equEntrance);

    /**
     * 修改门禁设备信息
     *
     * @param equEntrance 门禁设备信息
     * @return 结果
     */
    public int updateEquEntrance(EquEntrance equEntrance);

    /**
     * 删除门禁设备信息
     *
     * @param enId 门禁设备信息ID
     * @return 结果
     */
    public int deleteEquEntranceById(Long enId);

    /**
     * 批量删除门禁设备信息
     *
     * @param enIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEquEntranceByIds(Long[] enIds);
}
