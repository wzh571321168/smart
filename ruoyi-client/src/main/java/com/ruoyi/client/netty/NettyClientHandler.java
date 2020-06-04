package com.ruoyi.client.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.client.handler.BaseMsgHandler;
import com.ruoyi.common.enums.ModuleKeyEnum;
import com.ruoyi.common.utils.SpringUtil;
import com.ruoyi.common.vo.NettyMsgVo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 客户端处理类
 **/

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private String clientId;

    public NettyClientHandler(String clientId){
        this.clientId=clientId;
    }

    /**
     * 计算有多少客户端接入，第一个string为客户端ip
     */
    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //CLIENT_MAP.put(ctx.channel().id(), ctx);
        ctx.channel().writeAndFlush(Unpooled.buffer().writeBytes(("id="+clientId).getBytes()));
        //super.channelActive(ctx);
        log.info("ClientHandler Active");
    }
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            NettyMsgVo nettyMsgVo = JSONObject.parseObject(msg.toString(), NettyMsgVo.class);
            BaseMsgHandler bean = (BaseMsgHandler) SpringUtil.getBean(ModuleKeyEnum.getEnum(nettyMsgVo.getModuleKey()).getBeanName());
            bean.msgHandler(JSON.toJSONString(nettyMsgVo.getData()));
            //do something msg
            /*ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String request = new String(data, "utf-8");*/
            System.out.println("Client: " + msg);
           // ctx.writeAndFlush("收到");

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有服务端端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        ctx.close();
        log.info("服务端终止了服务");
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        //cause.printStackTrace();
        log.info("服务端发生异常【" + cause.getMessage() + "】");
        ctx.close();
    }

    /**
     * @param msg       需要发送的消息内容
     * @param channelId 连接通道唯一id
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 客户端给服务端发送消息
     * @return: void
     */
    public void channelWrite(ChannelId channelId, String msg) {

        ChannelHandlerContext ctx = CLIENT_MAP.get(channelId);

        if (ctx == null) {
            log.info("通道【" + channelId + "】不存在");
            return;
        }

        //将客户端的信息直接返回写入ctx
        ctx.write(msg + " 时间：" + System.currentTimeMillis());
        //刷新缓存区
        ctx.flush();
    }



}
