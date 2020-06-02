package com.ruoyi.system.nettyserver;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 服务器Channel通道初始化设置
 */
@ChannelHandler.Sharable
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        /*ChannelPipeline pipeline = socketChannel.pipeline();

          *//* LineBasedFrameDecoder的工作原理是：依次遍历ByteBuf中的可读字节，
        判断看其是否有”\n” 或 “\r\n”， 如果有就以此位置为结束位置。
        从可读索引到结束位置的区间的字节就组成了一行。 它是以换行符为结束标志的解码器，
        支持携带结束符和不带结束符两种解码方式，同时支持配置单行的最大长度，
        如果读到了最大长度之后仍然没有发现换行符，则抛出异常，同时忽略掉之前读到的异常码流。*//*
        pipeline.addLast(new LineBasedFrameDecoder(10010));
        //字符串解码和编码
        //LineBasedFrameDecoder + StringDecoder 就是一个按行切换的文本解码器。
        pipeline.addLast( new StringDecoder());
        pipeline.addLast( new StringEncoder());
        //服务器的逻辑
        pipeline.addLast("handler", new NioServerHandler());*/
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        //pipeline.addLast("idleStateHandler", new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
        // netty基于分割符的自带解码器，根据提供的分隔符解析报文，这里是0x7e;1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
//        pipeline.addLast(
//                new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e }),
//                        Unpooled.copiedBuffer(new byte[] { 0x7e })));
        //pipeline.addLast(new LineBasedFrameDecoder(10010));
        //自定义编解码器
        pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
        //自定义Hadler
        pipeline.addLast("handler",new NioServerHandler());
        //自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
        //pipeline.addLast(group,"BussinessHandler",new BussinessHandler());
    }


}
