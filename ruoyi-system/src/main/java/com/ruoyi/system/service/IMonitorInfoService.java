package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MonitorInfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
public interface IMonitorInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public MonitorInfo selectMonitorInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<MonitorInfo> selectMonitorInfoList(MonitorInfo monitorInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertMonitorInfo(MonitorInfo monitorInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateMonitorInfo(MonitorInfo monitorInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMonitorInfoByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteMonitorInfoById(Long id);
}
