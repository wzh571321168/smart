package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ClientServerMapper;
import com.ruoyi.system.domain.ClientServer;
import com.ruoyi.system.service.IClientServerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
@Service
public class ClientServerServiceImpl implements IClientServerService 
{
    @Autowired
    private ClientServerMapper clientServerMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ClientServer selectClientServerById(Long id)
    {
        return clientServerMapper.selectClientServerById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param clientServer 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ClientServer> selectClientServerList(ClientServer clientServer)
    {
        return clientServerMapper.selectClientServerList(clientServer);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param clientServer 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertClientServer(ClientServer clientServer)
    {
        return clientServerMapper.insertClientServer(clientServer);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param clientServer 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateClientServer(ClientServer clientServer)
    {
        return clientServerMapper.updateClientServer(clientServer);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClientServerByIds(String ids)
    {
        return clientServerMapper.deleteClientServerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteClientServerById(Long id)
    {
        return clientServerMapper.deleteClientServerById(id);
    }
}
