package com.ruoyi.system.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.ruoyi.common.smartutils.MatUtils;
import com.ruoyi.common.vo.UploadExtraDataVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PicDiscernRecord;
import com.ruoyi.system.service.IPicDiscernRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

/**
 * 图片识别记录Controller
 * 
 * @author ruoyi
 * @date 2020-06-02
 */
@Controller
@RequestMapping("/system/picDiscernRecord")
public class PicDiscernRecordController extends BaseController
{
    private String prefix = "system/picDiscernRecord";

    @Autowired
    private IPicDiscernRecordService picDiscernRecordService;

    @RequiresPermissions("system:picDiscernRecord:view")
    @GetMapping()
    public String picDiscernRecord()
    {
        return prefix + "/picDiscernRecord";
    }

    /**
     * 查询图片识别记录列表
     */
    @RequiresPermissions("system:picDiscernRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PicDiscernRecord picDiscernRecord)
    {
        startPage();
        List<PicDiscernRecord> list = picDiscernRecordService.selectPicDiscernRecordList(picDiscernRecord);
        return getDataTable(list);
    }

    /**
     * 导出图片识别记录列表
     */
    @RequiresPermissions("system:picDiscernRecord:export")
    @Log(title = "图片识别记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PicDiscernRecord picDiscernRecord)
    {
        List<PicDiscernRecord> list = picDiscernRecordService.selectPicDiscernRecordList(picDiscernRecord);
        ExcelUtil<PicDiscernRecord> util = new ExcelUtil<PicDiscernRecord>(PicDiscernRecord.class);
        return util.exportExcel(list, "picDiscernRecord");
    }

    /**
     * 新增图片识别记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存图片识别记录
     */
    @RequiresPermissions("system:picDiscernRecord:add")
    @Log(title = "图片识别记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PicDiscernRecord picDiscernRecord)
    {
        return toAjax(picDiscernRecordService.insertPicDiscernRecord(picDiscernRecord));
    }

    /**
     * 修改图片识别记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PicDiscernRecord picDiscernRecord = picDiscernRecordService.selectPicDiscernRecordById(id);
        mmap.put("picDiscernRecord", picDiscernRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存图片识别记录
     */
    @RequiresPermissions("system:picDiscernRecord:edit")
    @Log(title = "图片识别记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PicDiscernRecord picDiscernRecord)
    {
        return toAjax(picDiscernRecordService.updatePicDiscernRecord(picDiscernRecord));
    }

    /**
     * 删除图片识别记录
     */
    @RequiresPermissions("system:picDiscernRecord:remove")
    @Log(title = "图片识别记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(picDiscernRecordService.deletePicDiscernRecordByIds(ids));
    }


    /**
     * 图片识别记录
     */
    @RequiresPermissions("system:picDiscernRecord:discern")
    @Log(title = "图片识别记录", businessType = BusinessType.DELETE)
    @PostMapping( "/discern")
    @ResponseBody
    public Object discern(MultipartFile[] files,String type)
    {
        Object o = null;
        try {
            o = picDiscernRecordService.picDisCern(type, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
}
