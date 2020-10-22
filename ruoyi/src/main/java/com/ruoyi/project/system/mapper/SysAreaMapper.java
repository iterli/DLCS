package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysArea;

import java.util.List;

/**
 * 区域Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-09
 */
public interface SysAreaMapper
{
    /**
     * 查询区域
     *
     * @param areaId 区域ID
     * @return 区域
     */
    public SysArea selectSysAreaById(Long areaId);

    /**
     * 查询区域列表
     *
     * @param sysArea 区域
     * @return 区域集合
     */
    public List<SysArea> selectSysAreaList(SysArea sysArea);

    /**
     * 新增区域
     *
     * @param sysArea 区域
     * @return 结果
     */
    public int insertSysArea(SysArea sysArea);

    /**
     * 修改区域
     *
     * @param sysArea 区域
     * @return 结果
     */
    public int updateSysArea(SysArea sysArea);

    /**
     * 删除区域
     *
     * @param areaId 区域ID
     * @return 结果
     */
    public int deleteSysAreaById(Long areaId);

    /**
     * 批量删除区域
     *
     * @param areaIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAreaByIds(Long[] areaIds);

}
