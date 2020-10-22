package com.ruoyi.project.equipment.controller;

import com.google.gson.Gson;
import com.ruoyi.common.connection.ConnectionMJUtil;
import com.ruoyi.common.json.JsonResult;
import com.ruoyi.common.utils.JsonUtil;
import com.ruoyi.common.utils.RegexUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.equipment.domain.EquFacemachine;
import com.ruoyi.project.equipment.service.IEquFacemachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 人脸设备信息Controller
 *
 * @author ruoyi
 * @date 2020-10-19
 */
@RestController
@RequestMapping("/equipment/facemachine")
public class EquFacemachineController extends BaseController
{
    @Autowired
    private IEquFacemachineService equFacemachineService;

    /**
     * 查询人脸设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquFacemachine equFacemachine)
    {
        startPage();
        List<EquFacemachine> list = equFacemachineService.selectEquFacemachineList(equFacemachine);
        return getDataTable(list);
    }

    /**
     * 导出人脸设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:export')")
    @Log(title = "人脸设备信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EquFacemachine equFacemachine)
    {
        List<EquFacemachine> list = equFacemachineService.selectEquFacemachineList(equFacemachine);
        ExcelUtil<EquFacemachine> util = new ExcelUtil<EquFacemachine>(EquFacemachine.class);
        return util.exportExcel(list, "facemachine");
    }

    /**
     * 获取人脸设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:query')")
    @GetMapping(value = "/{faceId}")
    public AjaxResult getInfo(@PathVariable("faceId") Long faceId)
    {
        return AjaxResult.success(equFacemachineService.selectEquFacemachineById(faceId));
    }

    /**
     * 新增人脸设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:add')")
    @Log(title = "人脸设备信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquFacemachine equFacemachine)
    {
        return toAjax(equFacemachineService.insertEquFacemachine(equFacemachine));
    }

    /**
     * 修改人脸设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:edit')")
    @Log(title = "人脸设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquFacemachine equFacemachine)
    {
        return toAjax(equFacemachineService.updateEquFacemachine(equFacemachine));
    }

    /**
     * 删除人脸设备信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:remove')")
    @Log(title = "人脸设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{faceIds}")
    public AjaxResult remove(@PathVariable Long[] faceIds)
    {
        return toAjax(equFacemachineService.deleteEquFacemachineByIds(faceIds));
    }

    /**
     * 获取人脸设备基本参数信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:facemachine:Inforation')")
    @PostMapping("/getInforation/{faceIds}")
    public JsonUtil getFaceInforation(EquFacemachine equFacemachine, HttpServletRequest request) throws Exception {
       JsonUtil jsonUtil = new JsonUtil();
       String localIp = request.getLocalAddr();
       //获取设备IP
       equFacemachine = equFacemachineService.selectEquFacemachineById(equFacemachine.getFaceId());
       String ip = equFacemachine.getFaceIp();
       if(!StringUtils.isEmpty(ip)){
           if(RegexUtil.validIp(ip)){
               String returnJsonStr;
               String url = "http://"+ip+":8090/getSerial"; //获取设备序列号
               Gson gson = new Gson();
               returnJsonStr = ConnectionMJUtil.connectFaceMachine(url,null,ConnectionMJUtil.REQ_POST);
               JsonResult result = gson.fromJson(returnJsonStr,JsonResult.class);
               if(result.getResult() == 1 && result.getCode().equals("000") && result.isSuccess() == true){
                   jsonUtil.setCode(0);
                   jsonUtil.setMsg("获取设备序列号");
                   String serialKey = String.valueOf(result.getData() == null ? "" : result.getData());
                   if(serialKey.equals("")){//{"code":"000","data":"30240BCB76D2895D","msg":"success","result":1,"success":true}
                       jsonUtil.setCode(1);
                       jsonUtil.setMsg("获取设备序列号失败:"+result.getMsg());
                   }else{
                       //更新设备序列号
                       equFacemachine.setSerial(serialKey);
                       equFacemachineService.updateEquFacemachine(equFacemachine);
                       if(result.getResult() == 1 && result.getCode().equals("000") && result.isSuccess() == true){
                           jsonUtil.setCode(0);
                           jsonUtil.setMsg("配置成功");
                           equFacemachine.setCallbackurl(String.valueOf(result.getData()));
                           equFacemachineService.updateEquFacemachine(equFacemachine);
                       }else{
                           jsonUtil.setCode(1);
                           jsonUtil.setMsg("获取设备序列号成功，配置配置回调地址失败 : " + result.getMsg());
                       }
                   }
               }else{
                   jsonUtil.setCode(1);
                   jsonUtil.setMsg("获取设备序列号失败 : " + result.getMsg());
               }
           }else{
               jsonUtil.setCode(1);
               jsonUtil.setMsg("IP地址不正确");
           }
       }else{
           jsonUtil.setCode(1);
           jsonUtil.setMsg("未能获取到IP");
       }
       return  jsonUtil;
   }
}























