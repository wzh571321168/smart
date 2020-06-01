package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ClientServer;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-06-01
 */
public interface ClientServerMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ClientServer selectClientServerById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param clientServer 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ClientServer> selectClientServerList(ClientServer clientServer);

    /**
     * 新增【请填写功能名称】
     * 
     * @param clientServer 【请填写功能名称】
     * @return 结果
     */
    public int insertClientServer(ClientServer clientServer);

    /**
     * 修改【请填写功能名称】
     * 
     * @param clientServer 【请填写功能名称】
     * @return 结果
     */
    public int updateClientServer(ClientServer clientServer);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteClientServerById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteClientServerByIds(String[] ids);
}
