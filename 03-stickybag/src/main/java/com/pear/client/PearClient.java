package com.pear.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lizhuo
 * @Description: netty client demo
 * @date 2020-02-29 18:35
 */
public class PearClient {

	public static void main(String[] args) throws InterruptedException {

		NioEventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {

						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new StringEncoder());
							pipeline.addLast(new PearClientHandler());
						}

					});

			ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
			System.out.println("connect to localhost:8888");

			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
			System.out.println("disconnect to localhost:8888");
		}
	}

}
