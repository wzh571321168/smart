package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * rtmp服务对象 rtmp_server
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
public class RtmpServer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 服务地址 */
    @Excel(name = "服务地址")
    private String serverAddress;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String isUsed;

    /** 删除标志 */
    @Excel(name = "删除标志")
    private String flag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setServerAddress(String serverAddress) 
    {
        this.serverAddress = serverAddress;
    }

    public String getServerAddress() 
    {
        return serverAddress;
    }
    public void setIsUsed(String isUsed) 
    {
        this.isUsed = isUsed;
    }

    public String getIsUsed() 
    {
        return isUsed;
    }
    public void setFlag(String flag) 
    {
        this.flag = flag;
    }

    public String getFlag() 
    {
        return flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serverAddress", getServerAddress())
            .append("isUsed", getIsUsed())
            .append("flag", getFlag())
            .toString();
    }
}
