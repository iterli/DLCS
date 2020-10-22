package com.ruoyi.project.equipment.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.equipment.domain.EquEntrance;
import com.ruoyi.project.equipment.service.IEquEntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门禁设备信息Controller
 *
 * @author ruoyi
 * @date 2020-10-19
 */
@RestController
@RequestMapping("/equipment/entrance")
public class EquEntranceController extends BaseController
{
    @Autowired
    private IEquEntranceService equEntranceService;

    /**
     * 查询门禁设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquEntrance equEntrance)
    {
        startPage();
        List<EquEntrance> list = equEntranceService.selectEquEntranceList(equEntrance);
        return getDataTable(list);
    }

    /**
     * 导出门禁设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:export')")
    @Log(title = "门禁设备信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EquEntrance equEntrance)
    {
        List<EquEntrance> list = equEntranceService.selectEquEntranceList(equEntrance);
        ExcelUtil<EquEntrance> util = new ExcelUtil<EquEntrance>(EquEntrance.class);
        return util.exportExcel(list, "entrance");
    }

    /**
     * 获取门禁设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:query')")
    @GetMapping(value = "/{enId}")
    public AjaxResult getInfo(@PathVariable("enId") Long enId)
    {
        return AjaxResult.success(equEntranceService.selectEquEntranceById(enId));
    }

    /**
     * 新增门禁设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:add')")
    @Log(title = "门禁设备信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquEntrance equEntrance)
    {
        return toAjax(equEntranceService.insertEquEntrance(equEntrance));
    }

    /**
     * 修改门禁设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:edit')")
    @Log(title = "门禁设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquEntrance equEntrance)
    {
        return toAjax(equEntranceService.updateEquEntrance(equEntrance));
    }

    /**
     * 删除门禁设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:entrance:remove')")
    @Log(title = "门禁设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{enIds}")
    public AjaxResult remove(@PathVariable Long[] enIds)
    {
        return toAjax(equEntranceService.deleteEquEntranceByIds(enIds));
    }
}
