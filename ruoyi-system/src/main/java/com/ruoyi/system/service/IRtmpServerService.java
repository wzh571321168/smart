package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RtmpServer;

/**
 * rtmp服务Service接口
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
public interface IRtmpServerService 
{
    /**
     * 查询rtmp服务
     * 
     * @param id rtmp服务ID
     * @return rtmp服务
     */
    public RtmpServer selectRtmpServerById(Long id);

    /**
     * 查询rtmp服务列表
     * 
     * @param rtmpServer rtmp服务
     * @return rtmp服务集合
     */
    public List<RtmpServer> selectRtmpServerList(RtmpServer rtmpServer);

    /**
     * 新增rtmp服务
     * 
     * @param rtmpServer rtmp服务
     * @return 结果
     */
    public int insertRtmpServer(RtmpServer rtmpServer);

    /**
     * 修改rtmp服务
     * 
     * @param rtmpServer rtmp服务
     * @return 结果
     */
    public int updateRtmpServer(RtmpServer rtmpServer);

    /**
     * 批量删除rtmp服务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRtmpServerByIds(String ids);

    /**
     * 删除rtmp服务信息
     * 
     * @param id rtmp服务ID
     * @return 结果
     */
    public int deleteRtmpServerById(Long id);
}
