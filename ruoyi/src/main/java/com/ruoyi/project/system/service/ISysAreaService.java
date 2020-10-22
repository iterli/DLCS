package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.system.domain.SysArea;

import java.util.List;

/**
 * 区域Service接口
 *
 * @author ruoyi
 * @date 2020-10-09
 */
public interface ISysAreaService
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
     * 修改区域状态
     *
     * @param area 用户信息
     * @return 结果
     */
    public int updateAreaStatus(SysArea area);

    /**
     * 批量删除区域
     *
     * @param areaIds 需要删除的区域ID
     * @return 结果
     */
    public int deleteSysAreaByIds(Long[] areaIds);

    /**
     * 删除区域信息
     *
     * @param areaId 区域ID
     * @return 结果
     */
    public int deleteSysAreaById(Long areaId);
    /**
     * 构建前端所需要下拉树结构
     *
     * @param areas 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildAreaTreeSelect(List<SysArea> areas);


    /**
     * 构建前端所需要树结构
     *
     * @param areas 部门列表
     * @return 树结构列表
     */
    public List<SysArea> buildAreaTree(List<SysArea> areas);
}
