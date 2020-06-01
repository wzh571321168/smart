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
import com.ruoyi.system.domain.ClientServer;
import com.ruoyi.system.service.IClientServerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * client服务Controller
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
@Controller
@RequestMapping("/system/clientServer")
public class ClientServerController extends BaseController
{
    private String prefix = "system/clientServer";

    @Autowired
    private IClientServerService clientServerService;

    @RequiresPermissions("system:clientServer:view")
    @GetMapping()
    public String clientServer()
    {
        return prefix + "/clientServer";
    }

    /**
     * 查询client服务列表
     */
    @RequiresPermissions("system:clientServer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClientServer clientServer)
    {
        startPage();
        List<ClientServer> list = clientServerService.selectClientServerList(clientServer);
        return getDataTable(list);
    }

    /**
     * 导出client服务列表
     */
    @RequiresPermissions("system:clientServer:export")
    @Log(title = "client服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClientServer clientServer)
    {
        List<ClientServer> list = clientServerService.selectClientServerList(clientServer);
        ExcelUtil<ClientServer> util = new ExcelUtil<ClientServer>(ClientServer.class);
        return util.exportExcel(list, "clientServer");
    }

    /**
     * 新增client服务
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存client服务
     */
    @RequiresPermissions("system:clientServer:add")
    @Log(title = "client服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ClientServer clientServer)
    {
        return toAjax(clientServerService.insertClientServer(clientServer));
    }

    /**
     * 修改client服务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ClientServer clientServer = clientServerService.selectClientServerById(id);
        mmap.put("clientServer", clientServer);
        return prefix + "/edit";
    }

    /**
     * 修改保存client服务
     */
    @RequiresPermissions("system:clientServer:edit")
    @Log(title = "client服务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ClientServer clientServer)
    {
        return toAjax(clientServerService.updateClientServer(clientServer));
    }

    /**
     * 删除client服务
     */
    @RequiresPermissions("system:clientServer:remove")
    @Log(title = "client服务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(clientServerService.deleteClientServerByIds(ids));
    }
}
