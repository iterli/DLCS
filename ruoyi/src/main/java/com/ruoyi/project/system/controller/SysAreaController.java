package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysArea;
import com.ruoyi.project.system.service.ISysAreaService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 区域Controller
 *
 * @author ruoyi
 * @date 2020-10-09
 */
@RestController
@RequestMapping("/system/area")
public class SysAreaController extends BaseController {
    @Autowired
    private ISysAreaService sysAreaService;


    /**
     * 查询区域列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysArea sysArea) {
        startPage();
        List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
        return getDataTable(list);
    }

    /**
     * 导出区域列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:export')")
    @Log(title = "区域", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysArea sysArea) {
        List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
        ExcelUtil<SysArea> util = new ExcelUtil<SysArea>(SysArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 获取区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:query')")
    @GetMapping(value = "/{areaId}")
    public AjaxResult getInfo(@PathVariable("areaId") Long areaId) {
        return AjaxResult.success(sysAreaService.selectSysAreaById(areaId));
    }

    /**
     * 新增区域
     */
    @PreAuthorize("@ss.hasPermi('system:area:add')")
    @Log(title = "区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysArea sysArea) {
        return toAjax(sysAreaService.insertSysArea(sysArea));
    }

    /**
     * 修改区域
     */
    @PreAuthorize("@ss.hasPermi('system:area:edit')")
    @Log(title = "区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysArea sysArea) {
        return toAjax(sysAreaService.updateSysArea(sysArea));
    }

    /**
     * 删除区域
     */
    @PreAuthorize("@ss.hasPermi('system:area:remove')")
    @Log(title = "区域", businessType = BusinessType.DELETE)
    @DeleteMapping("/{areaIds}")
    public AjaxResult remove(@PathVariable Long[] areaIds) {
        return toAjax(sysAreaService.deleteSysAreaByIds(areaIds));
    }

    /**
     * 获取区域下拉列表
     *
     * @return
     */

    @GetMapping("/treeselect")
    public AjaxResult getTreeArea(SysArea area) {
        List<SysArea> areas = sysAreaService.selectSysAreaList(area);
        return AjaxResult.success(sysAreaService.buildAreaTreeSelect(areas));
    }


    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:area:list')")
    @GetMapping("/list/exclude/{areaId}")
    public AjaxResult excludeChild(@PathVariable(value = "areaId", required = false) Long areaId) {
        List<SysArea> areas = sysAreaService.selectSysAreaList(new SysArea());
        Iterator<SysArea> it = areas.iterator();
        while (it.hasNext()) {
            SysArea area = (SysArea) it.next();
            if (area.getAreaId().intValue() == areaId
                    || ArrayUtils.contains(StringUtils.split(area.getAncestors(), ","), areaId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(areas);
    }


    /**
     * 修改区域状态
     */
    @PreAuthorize("@ss.hasPermi('system:area:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeAreaStatus(@RequestBody SysArea area) {
        return toAjax(sysAreaService.updateAreaStatus(area));
    }

}
