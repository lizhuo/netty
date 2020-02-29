package com.pear.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lizhuo
 * @Description: netty server demo
 * @date 2020-02-29 13:14
 */
public class PearServer {

	public static void main(String[] args) throws InterruptedException {

		// 用于处理连接
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		// 用于处理请求
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {
			// 用于启动 ServerChannel
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(parentGroup, childGroup)  // specify channel
				.channel(NioServerSocketChannel.class) // specify nio to communicate
				.childHandler(new PearChannelInitializer());  // 指定childGroup中的eventLoop所绑定的线程要执行的处理器

			// 指定当前服务器监听端口
			// bind 方法执行是异步的
			// sync 会使 bind 操作与后续代码执行由异步变成同步
			ChannelFuture future = bootstrap.bind(8888).sync();
			System.out.println("服务器启动成功 监听:8888");

			// 关闭 channel
			// closeFuture 执行也是异步的
			// 当channel 调用了 close 方法并关闭成功后才会触发 closeFuture
			future.channel().closeFuture().sync();

		} finally {
			// 优雅关闭
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}

	}

}
