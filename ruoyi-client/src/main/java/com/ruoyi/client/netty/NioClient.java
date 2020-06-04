package com.ruoyi.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class NioClient {
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private Integer port;
    @Value("${netty.clientId}")
    private String clientId;

    private EventLoopGroup group =null;
    @PostConstruct
    public void start(){
        // Configure the client.
        group = new NioEventLoopGroup();
        try {

            int num = 0;
            boolean boo =true;

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyClientChannelInitializer(clientId));
            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();
            /*future.channel().writeAndFlush(Unpooled.buffer().writeBytes(("id="+clientId).getBytes()));
            future.channel().closeFuture().sync();*/

            System.out.println(111);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("netty服务异常");
        }

    }
    @PreDestroy
    public void destroy(){
        group.shutdownGracefully();
    }
}
