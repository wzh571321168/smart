package com.ruoyi.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @description: 客户端初始化，客户端与服务器端连接一旦创建，这个类中方法就会被回调，设置出站编码器和入站解码器，客户端服务端编解码要一致
 **/

public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private String clientId;

    public NettyClientChannelInitializer(String clientId) {
        this.clientId = clientId;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //channel.pipeline().addLast(new LineBasedFrameDecoder(10010));
        channel.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));

        channel.pipeline().addLast(new NettyClientHandler(clientId));
    }
}
