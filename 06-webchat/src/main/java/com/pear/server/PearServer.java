package com.pear.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lizhuo
 * @Description: netty server web chat demo server
 * @date 2020-02-29 17:52
 */
public class PearServer {

	public static void main(String[] args) throws InterruptedException {

		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup clientGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(parentGroup, clientGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 添加基于行的解码器
							pipeline.addLast(new LineBasedFrameDecoder(2048));
							// StringDecoder 字符串解码器：将 channel 中的 ByteBuf 数据解码为 String
							pipeline.addLast(new StringDecoder());
							// StringEncoder 自负床编码器：将 String 编码 为ByteBuf 发送到 Channel 中
							pipeline.addLast(new StringEncoder());

							pipeline.addLast(new PearHandler());
						}

					});

			ChannelFuture future = bootstrap.bind(8888).sync();
			System.out.println("Server started listening 8888");

			future.channel().closeFuture().sync();
		} finally {
			parentGroup.shutdownGracefully();
			clientGroup.shutdownGracefully();
			System.out.println("Server shutdown");
		}
	}

}
