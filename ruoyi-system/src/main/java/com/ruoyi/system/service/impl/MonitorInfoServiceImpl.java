package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MonitorInfoMapper;
import com.ruoyi.system.domain.MonitorInfo;
import com.ruoyi.system.service.IMonitorInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
@Service
public class MonitorInfoServiceImpl implements IMonitorInfoService 
{
    @Autowired
    private MonitorInfoMapper monitorInfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public MonitorInfo selectMonitorInfoById(Long id)
    {
        return monitorInfoMapper.selectMonitorInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<MonitorInfo> selectMonitorInfoList(MonitorInfo monitorInfo)
    {
        return monitorInfoMapper.selectMonitorInfoList(monitorInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertMonitorInfo(MonitorInfo monitorInfo)
    {
        return monitorInfoMapper.insertMonitorInfo(monitorInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param monitorInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateMonitorInfo(MonitorInfo monitorInfo)
    {
        return monitorInfoMapper.updateMonitorInfo(monitorInfo);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMonitorInfoByIds(String ids)
    {
        return monitorInfoMapper.deleteMonitorInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteMonitorInfoById(Long id)
    {
        return monitorInfoMapper.deleteMonitorInfoById(id);
    }
}
