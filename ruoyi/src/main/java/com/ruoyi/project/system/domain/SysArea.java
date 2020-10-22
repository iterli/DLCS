package com.ruoyi.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域对象 sys_area
 *
 * @author ruoyi
 * @date 2020-10-09
 */
public class SysArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 区域id */
    private Long areaId;

    /** 父区域id */
    @Excel(name = "父区域id")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String areaName;

    /** 区域类型 */
    @Excel(name = "区域类型")
    private String areaType;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long orderNum;

    /** 区域状态（0正常 3停用） */
    @Excel(name = "区域状态", readConverterExp = "0=正常,3=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private  List<SysArea> area;

    public List<SysArea> getArea() {
        return area;
    }

    public void setArea(List<SysArea> area) {
        this.area = area;
    }

    /** 子部门 */
    private List<SysArea> children = new ArrayList<SysArea>();
    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }

    public Long getAreaId()
    {
        return areaId;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }
    public List<SysArea> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysArea> children)
    {
        this.children = children;
    }
    public String getAreaName()
    {
        return areaName;
    }
    public void setAreaType(String areaType)
    {
        this.areaType = areaType;
    }

    public String getAreaType()
    {
        return areaType;
    }
    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("areaId", getAreaId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("areaName", getAreaName())
                .append("areaType", getAreaType())
                .append("orderNum", getOrderNum())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
