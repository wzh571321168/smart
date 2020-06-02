package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图片识别记录对象 pic_discern_record
 * 
 * @author ruoyi
 * @date 2020-06-02
 */
public class PicDiscernRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String picPath;

    /** 处理的地址 */
    @Excel(name = "处理的地址")
    private String path;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 识别结果 */
    @Excel(name = "识别结果")
    private String result;

    /** 识别时间 */
    @Excel(name = "识别时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discernTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPicPath(String picPath) 
    {
        this.picPath = picPath;
    }

    public String getPicPath() 
    {
        return picPath;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setDiscernTime(Date discernTime) 
    {
        this.discernTime = discernTime;
    }

    public Date getDiscernTime() 
    {
        return discernTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("picPath", getPicPath())
            .append("path", getPath())
            .append("type", getType())
            .append("result", getResult())
            .append("discernTime", getDiscernTime())
            .toString();
    }
}
