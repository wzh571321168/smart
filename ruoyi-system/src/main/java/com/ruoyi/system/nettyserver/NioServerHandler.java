package com.ruoyi.system.nettyserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 服务业务实现
 */
@Slf4j
public class NioServerHandler  extends ChannelInboundHandlerAdapter {
    //  private static final Logger logger = LoggerFactory.getLogger(NioServerHandler.class);

    /**
     *读取客户端发来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String id = msg.toString().replace("id=","");
        log.info("接收客户端消息："+id);
        ctx.writeAndFlush(Unpooled.copiedBuffer("收到了".getBytes()));
        if(id !=null &&!id.equals("")) {
            if (NioServer.map.get(id) != null && NioServer.map.get(id).equals(ctx)) {

                //接收到服务端发来的数据进行业务处理
                //     logger.info("接收数据成功！" + DateUtils.dateToString(new Date()));
            } else {
                //接收到服务端发来的数据进行业务处理
                //如果map中没有此ctx 将连接存入map中
                NioServer.map.put(id, ctx);
                //   logger.info("连接成功，加入map管理连接！"+"mn:" +mn+" " +""+ DateUtils.dateToString(new Date()));
            }
        }else{
            //logger.info(""+"不是监测数据"+ msg.toString()+" "+ DateUtils.dateToString(new Date()));
        }
        // ctx.writeAndFlush("Received your message : " + msg.toString());
    }


    /*    *//**
     * 读取完毕客户端发送过来的数据之后的操作
     *//*
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        //  ctx.channel().write("");
        //  ctx.channel().flush();
    }*/

    /**
     * 客户端主动断开服务端的链接,关闭流
     * */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
        removeChannnelMap(ctx);
        // 关闭流
        ctx.close();
    }

    /**
     * 客户端主动连接服务端
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // logger.info("RemoteAddress"+ ctx.channel().remoteAddress() + " active !");
        // logger.info("msg"+ ctx.m + " active !");
        //  ctx.writeAndFlush("连接成功！");
        super.channelActive(ctx);
    }

    /**
     * 发生异常处理
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // logger.error("连接异常,连接异常："+ DateUtils.dateToString(new Date())+cause.getMessage(), cause);
        ctx.fireExceptionCaught(cause);
        //   removeChannnelMap(ctx);
        //  ctx.close();
    }

    /**
     *删除map中ChannelHandlerContext
     *  */
    private void removeChannnelMap(ChannelHandlerContext ctx){
        String removeKey=null;
        for( String key :NioServer.map.keySet()){
            if( NioServer.map.get(key)!=null &&  NioServer.map.get(key).equals( ctx)){
                removeKey=key;
                break;
            }
        }
        NioServer.map.remove(removeKey);
        log.info("客户端：{}退出连接",removeKey);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE))
                ctx.close();
            //标志该链接已经close 了 
        }
    }

}