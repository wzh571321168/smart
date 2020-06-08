package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.ModuleKeyEnum;
import com.ruoyi.common.vo.MonitorVo;
import com.ruoyi.system.domain.MonitorInfo;
import com.ruoyi.system.mapper.ClientServerMapper;
import com.ruoyi.system.mapper.MonitorInfoMapper;
import com.ruoyi.system.nettyserver.NioServer;
import com.ruoyi.system.service.ICallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CallbackServiceImpl implements ICallbackService {

    @Autowired
    private MonitorInfoMapper rtspAddressMapper;
    @Autowired
    private ClientServerMapper clientServerMapper;

    public void push( String uid,Boolean type) {
        //查询数据库是否存在该rtsp地址，如果存在，调用推流客户端服务，不存在，不处理
        MonitorInfo monitorInfo=new MonitorInfo();
        monitorInfo.setStatus("Y");
        monitorInfo.setUid(uid);
        List<MonitorInfo> monitorInfos = rtspAddressMapper.selectMonitorInfoList(monitorInfo);
        if(monitorInfos!=null){
            monitorInfo=monitorInfos.get(0);
            Map<String,Object> map=new HashMap<>();
            map.put("rtspUrl",monitorInfo.getRtspAddress());
            MonitorVo monitorVo=new MonitorVo();
            monitorVo.setRtspAddress(monitorInfo.getRtspAddress());
            String clientNum = clientServerMapper.selectClientServerById(monitorInfo.getClientId()).getClientNum();
            //monitorVo.setClientNum();
            monitorVo.setRtmpServer("rtmp://127.0.0.1:1935/stream/test");
            monitorVo.setUid(monitorInfo.getUid());
            monitorVo.setFlag(type);
            if(NioServer.map.get(clientNum)!=null) {
                NioServer.sendMsg(clientNum, ModuleKeyEnum.STREEM_CALLBACK.getCode(),monitorVo);
            }else {
                log.info("客户端{}不在线",clientNum);
            }
        }
    }
}
