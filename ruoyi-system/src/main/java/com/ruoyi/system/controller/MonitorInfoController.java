package com.ruoyi.system.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.MonitorInfo;
import com.ruoyi.system.service.IMonitorInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 监控信息Controller
 * 
 * @author wang
 * @date 2020-06-01
 */
@Controller
@RequestMapping("/system/monitorInfo")
public class MonitorInfoController extends BaseController
{
    private String prefix = "system/monitorInfo";

    @Autowired
    private IMonitorInfoService monitorInfoService;

    @RequiresPermissions("system:monitorInfo:view")
    @GetMapping()
    public String monitorInfo()
    {
        return prefix + "/monitorInfo";
    }

    /**
     * 查询监控信息列表
     */
    @RequiresPermissions("system:monitorInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MonitorInfo monitorInfo)
    {
        startPage();
        List<MonitorInfo> list = monitorInfoService.selectMonitorInfoList(monitorInfo);
        return getDataTable(list);
    }

    /**
     * 导出监控信息列表
     */
    @RequiresPermissions("system:monitorInfo:export")
    @Log(title = "监控信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MonitorInfo monitorInfo)
    {
        List<MonitorInfo> list = monitorInfoService.selectMonitorInfoList(monitorInfo);
        ExcelUtil<MonitorInfo> util = new ExcelUtil<MonitorInfo>(MonitorInfo.class);
        return util.exportExcel(list, "monitorInfo");
    }

    /**
     * 新增监控信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存监控信息
     */
    @RequiresPermissions("system:monitorInfo:add")
    @Log(title = "监控信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MonitorInfo monitorInfo)
    {
        return toAjax(monitorInfoService.insertMonitorInfo(monitorInfo));
    }

    /**
     * 修改监控信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MonitorInfo monitorInfo = monitorInfoService.selectMonitorInfoById(id);
        mmap.put("monitorInfo", monitorInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存监控信息
     */
    @RequiresPermissions("system:monitorInfo:edit")
    @Log(title = "监控信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonitorInfo monitorInfo)
    {
        return toAjax(monitorInfoService.updateMonitorInfo(monitorInfo));
    }

    /**
     * 删除监控信息
     */
    @RequiresPermissions("system:monitorInfo:remove")
    @Log(title = "监控信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(monitorInfoService.deleteMonitorInfoByIds(ids));
    }
}
