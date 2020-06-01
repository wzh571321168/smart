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
import com.ruoyi.system.domain.RtmpServer;
import com.ruoyi.system.service.IRtmpServerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * rtmp服务Controller
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
@Controller
@RequestMapping("/system/rtmpServer")
public class RtmpServerController extends BaseController
{
    private String prefix = "system/rtmpServer";

    @Autowired
    private IRtmpServerService rtmpServerService;

    @RequiresPermissions("system:rtmpServer:view")
    @GetMapping()
    public String rtmpServer()
    {
        return prefix + "/rtmpServer";
    }

    /**
     * 查询rtmp服务列表
     */
    @RequiresPermissions("system:rtmpServer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RtmpServer rtmpServer)
    {
        startPage();
        List<RtmpServer> list = rtmpServerService.selectRtmpServerList(rtmpServer);
        return getDataTable(list);
    }

    /**
     * 导出rtmp服务列表
     */
    @RequiresPermissions("system:rtmpServer:export")
    @Log(title = "rtmp服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RtmpServer rtmpServer)
    {
        List<RtmpServer> list = rtmpServerService.selectRtmpServerList(rtmpServer);
        ExcelUtil<RtmpServer> util = new ExcelUtil<RtmpServer>(RtmpServer.class);
        return util.exportExcel(list, "rtmpServer");
    }

    /**
     * 新增rtmp服务
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存rtmp服务
     */
    @RequiresPermissions("system:rtmpServer:add")
    @Log(title = "rtmp服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RtmpServer rtmpServer)
    {
        return toAjax(rtmpServerService.insertRtmpServer(rtmpServer));
    }

    /**
     * 修改rtmp服务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RtmpServer rtmpServer = rtmpServerService.selectRtmpServerById(id);
        mmap.put("rtmpServer", rtmpServer);
        return prefix + "/edit";
    }

    /**
     * 修改保存rtmp服务
     */
    @RequiresPermissions("system:rtmpServer:edit")
    @Log(title = "rtmp服务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RtmpServer rtmpServer)
    {
        return toAjax(rtmpServerService.updateRtmpServer(rtmpServer));
    }

    /**
     * 删除rtmp服务
     */
    @RequiresPermissions("system:rtmpServer:remove")
    @Log(title = "rtmp服务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rtmpServerService.deleteRtmpServerByIds(ids));
    }
}
