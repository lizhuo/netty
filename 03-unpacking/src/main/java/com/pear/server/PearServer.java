package com.pear.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lizhuo
 * @Description: netty server demo
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
							// StringDecoder 字符串解码器：将 channel 中的 ByteBuf 数据解码为 String
							pipeline.addLast(new StringDecoder());

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
