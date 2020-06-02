package com.ruoyi.system.nettyserver;

import com.alibaba.fastjson.JSON;

import com.ruoyi.common.vo.NettyMsgVo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * nio服务端
 */
@Component
@Slf4j
public class NioServer {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    //  private static final Logger logger = LoggerFactory.getLogger(NioServer.class);
    //连接map
    public  static Map<String, ChannelHandlerContext> map = new HashMap<String, ChannelHandlerContext>();
    @PostConstruct
    public void start(){
        bind(defaultPort);
    }
    //默认端口
    @Value("${netty.port}")
    private Integer defaultPort;
    public void bind(Integer port)  {
        try {
        //配置服务端的NIO线程组
         bossGroup = new NioEventLoopGroup();
         workGroup = new NioEventLoopGroup();

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup) // 绑定线程池
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true) // 2小时无数据激活心跳机制
                    .childHandler(new ServerChannelInitializer());
            // 服务器异步创建绑定
            if(null==port){
                port=this.defaultPort;
            }
            ChannelFuture f = b.bind(port).sync();
            log.info("netty服务启动，端口："+port);
            // 关闭服务器通道
            //f.channel().closeFuture().sync();
            System.out.println(111);
        } catch (Exception e){
            //  logger.info("服务停止："+ DateUtils.dateToString(new Date()));
           log.error("netty服务异常");
        }
    }
    @PreDestroy
    public void destroy(){
        // 释放线程池资源
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
    public static void sendMsg(String id,String moduleKey,Object o){
        map.get(id).writeAndFlush(JSON.toJSONString(new NettyMsgVo(moduleKey,o)));
    }
    private void initEventPool() {
        bossGroup=new NioEventLoopGroup();
        workGroup=new NioEventLoopGroup();
        /*if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }*/
    }
/*
    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }*/
}
