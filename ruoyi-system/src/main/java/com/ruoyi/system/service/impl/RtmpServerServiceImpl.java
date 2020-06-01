package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RtmpServerMapper;
import com.ruoyi.system.domain.RtmpServer;
import com.ruoyi.system.service.IRtmpServerService;
import com.ruoyi.common.core.text.Convert;

/**
 * rtmp服务Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
@Service
public class RtmpServerServiceImpl implements IRtmpServerService 
{
    @Autowired
    private RtmpServerMapper rtmpServerMapper;

    /**
     * 查询rtmp服务
     * 
     * @param id rtmp服务ID
     * @return rtmp服务
     */
    @Override
    public RtmpServer selectRtmpServerById(Long id)
    {
        return rtmpServerMapper.selectRtmpServerById(id);
    }

    /**
     * 查询rtmp服务列表
     * 
     * @param rtmpServer rtmp服务
     * @return rtmp服务
     */
    @Override
    public List<RtmpServer> selectRtmpServerList(RtmpServer rtmpServer)
    {
        return rtmpServerMapper.selectRtmpServerList(rtmpServer);
    }

    /**
     * 新增rtmp服务
     * 
     * @param rtmpServer rtmp服务
     * @return 结果
     */
    @Override
    public int insertRtmpServer(RtmpServer rtmpServer)
    {
        return rtmpServerMapper.insertRtmpServer(rtmpServer);
    }

    /**
     * 修改rtmp服务
     * 
     * @param rtmpServer rtmp服务
     * @return 结果
     */
    @Override
    public int updateRtmpServer(RtmpServer rtmpServer)
    {
        return rtmpServerMapper.updateRtmpServer(rtmpServer);
    }

    /**
     * 删除rtmp服务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRtmpServerByIds(String ids)
    {
        return rtmpServerMapper.deleteRtmpServerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除rtmp服务信息
     * 
     * @param id rtmp服务ID
     * @return 结果
     */
    @Override
    public int deleteRtmpServerById(Long id)
    {
        return rtmpServerMapper.deleteRtmpServerById(id);
    }
}
