package com.pear.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lizhuo
 * @Description: web socket  server demo
 * @date 2020-02-29 13:14
 */
public class PearServer {

	public static void main(String[] args) throws InterruptedException {

		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(parentGroup, childGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new PearChannelInitializer());

			ChannelFuture future = bootstrap.bind(8888).sync();
			System.out.println("服务器启动成功 监听:8888");

			future.channel().closeFuture().sync();

		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
			System.out.println("Server shutdown");
		}

	}

}
