package com.ruoyi.project.equipment.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.system.domain.SysArea;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 门禁设备信息对象 equ_entrance
 *
 * @author ruoyi
 * @date 2020-10-19
 */
public class EquEntrance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 门禁设备ID */
    private Long enId;

    /** 区域ID */
    @Excel(name = "区域ID")
    private Long areaId;

    /** 所在服务器id */
    @Excel(name = "所在服务器id")
    private Long infoId;

    /** 门禁设备名称 */
    @Excel(name = "门禁设备名称")
    private String enName;

    /** 门禁设备IP */
    @Excel(name = "门禁设备IP")
    private String enIp;

    /** 门禁设备版本 */
    @Excel(name = "门禁设备版本")
    private String version;

    /** 在线状态（0在线 1离线） */
    @Excel(name = "在线状态", readConverterExp = "0=在线,1=离线")
    private String linestatus;

    /**区域对象*/
    @Excel(name = "区域状态")
    private SysArea area;

    public SysArea getArea() {
        return area;
    }

    public void setArea(SysArea area) {
        this.area = area;
    }

    public void setEnId(Long enId)
    {
        this.enId = enId;
    }

    public Long getEnId()
    {
        return enId;
    }
    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }

    public Long getAreaId()
    {
        return areaId;
    }
    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public Long getInfoId()
    {
        return infoId;
    }
    public void setEnName(String enName)
    {
        this.enName = enName;
    }

    public String getEnName()
    {
        return enName;
    }
    public void setEnIp(String enIp)
    {
        this.enIp = enIp;
    }

    public String getEnIp()
    {
        return enIp;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }
    public void setLinestatus(String linestatus)
    {
        this.linestatus = linestatus;
    }

    public String getLinestatus()
    {
        return linestatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("enId", getEnId())
            .append("areaId", getAreaId())
            .append("infoId", getInfoId())
            .append("enName", getEnName())
            .append("enIp", getEnIp())
            .append("version", getVersion())
            .append("linestatus", getLinestatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
