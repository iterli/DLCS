package com.ruoyi.project.equipment.domain;

import com.ruoyi.project.system.domain.SysArea;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 人脸设备信息对象 equ_facemachine
 *
 * @author ruoyi
 * @date 2020-10-19
 */
public class EquFacemachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人脸设备ID */
    private Long faceId;

    /** 区域ID */
    @Excel(name = "区域ID")
    private Long areaId;

    /** 人脸设备名称 */
    @Excel(name = "人脸设备名称")
    private String faceName;

    /** 人脸设备IP */
    @Excel(name = "人脸设备IP")
    private String faceIp;

    /** 人脸设备序列号 */
    @Excel(name = "人脸设备序列号")
    private String serial;

    /** 人脸设备密码 */
    @Excel(name = "人脸设备密码")
    private String facePassword;

    /** 人脸设备回调地址 */
    @Excel(name = "人脸设备回调地址")
    private String callbackurl;

    /** 人脸设备上传照片数 */
    @Excel(name = "人脸设备上传照片数")
    private Long facePicturecount;

    /** 人脸设备特征数 */
    @Excel(name = "人脸设备特征数")
    private Long faceFeaturecount;

    /** 在线状态（0在线 1离线） */
    @Excel(name = "在线状态", readConverterExp = "0=在线,1=离线")
    private String linestatus;

    /** 人脸设备版本 */
    @Excel(name = "人脸设备版本")
    private String version;

    /**心跳地址*/
    @Excel(name = "人脸设备心跳地址")
    private String faceHeartbeatAddress;

    /**区域对象*/
    @Excel(name = "区域状态")
    private SysArea area;

    /**进出标志*/
    @Excel(name = "进出标志")
    private String enterInto;

    /**心跳地址*/
    @Excel(name = "心跳地址")
    private String faceHeartbeartAddress;


    public String getFaceHeartbeartAddress() {
        return faceHeartbeartAddress;
    }

    public void setFaceHeartbeartAddress(String faceHeartbeartAddress) {
        this.faceHeartbeartAddress = faceHeartbeartAddress;
    }


    public SysArea getArea() {
        return area;
    }
    public void setArea(SysArea area) {
        this.area = area;
    }
    public String getFaceHeartbeatAddress() {
        return faceHeartbeatAddress;
    }
    public void setFaceHeartbeatAddress(String faceHeartbeatAddress) {
        this.faceHeartbeatAddress = faceHeartbeatAddress;
    }
    public String getEnterInto() {
        return enterInto;
    }

    public void setEnterInto(String enterInto) {
        this.enterInto = enterInto;
    }

    public void setFaceId(Long faceId)
    {
        this.faceId = faceId;
    }

    public Long getFaceId()
    {
        return faceId;
    }
    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }

    public Long getAreaId()
    {
        return areaId;
    }
    public void setFaceName(String faceName)
    {
        this.faceName = faceName;
    }

    public String getFaceName()
    {
        return faceName;
    }
    public void setFaceIp(String faceIp)
    {
        this.faceIp = faceIp;
    }

    public String getFaceIp()
    {
        return faceIp;
    }
    public void setSerial(String serial)
    {
        this.serial = serial;
    }

    public String getSerial()
    {
        return serial;
    }
    public void setFacePassword(String facePassword)
    {
        this.facePassword = facePassword;
    }

    public String getFacePassword()
    {
        return facePassword;
    }
    public void setCallbackurl(String callbackurl)
    {
        this.callbackurl = callbackurl;
    }

    public String getCallbackurl()
    {
        return callbackurl;
    }
    public void setFacePicturecount(Long facePicturecount)
    {
        this.facePicturecount = facePicturecount;
    }

    public Long getFacePicturecount()
    {
        return facePicturecount;
    }
    public void setFaceFeaturecount(Long faceFeaturecount)
    {
        this.faceFeaturecount = faceFeaturecount;
    }

    public Long getFaceFeaturecount()
    {
        return faceFeaturecount;
    }
    public void setLinestatus(String linestatus)
    {
        this.linestatus = linestatus;
    }

    public String getLinestatus()
    {
        return linestatus;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("faceId", getFaceId())
            .append("areaId", getAreaId())
            .append("faceName", getFaceName())
            .append("faceIp", getFaceIp())
            .append("serial", getSerial())
            .append("facePassword", getFacePassword())
            .append("callbackurl", getCallbackurl())
            .append("facePicturecount", getFacePicturecount())
            .append("faceFeaturecount", getFaceFeaturecount())
            .append("linestatus", getLinestatus())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
