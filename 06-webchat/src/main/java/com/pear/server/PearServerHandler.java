package com.pear.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhuo
 * @Description: 自定义服务端处理器
 * 需求：实现处理 C/S 模式的 socket 通信 demo
 * @date 2020-02-29 18:21
 */
public class PearServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 创建一个channelGroup 其是一个线程安全的集合，其中存放着与当前服务器相连的所有Active状态的Channel
	 * GlobalEventExecutor 是一个单例单线程EventExecutor，是为了保证 对所有当前group中所有channel的
	 * 处理线程是同一个线程
	 */
	private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 只要有客户端Channel给当前的服务端发送了消息，那么就会触发该方法的执行
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		// 这里将要实现将消息广播给所有group中的客户端Channel
		// 发送给自己的消息与发送给大家的消息是不一样的
		group.forEach(ch -> {
			if (ch != channel ) {
				ch.writeAndFlush(channel.remoteAddress() + ": " +msg + "\n");
			} else {
				channel.writeAndFlush("me: " +msg + "\n");
			}
		});
	}

	/**
	 * 只要有客户端Channel与服务端连接成功就会执行这个方法
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 获取到与当前服务器连接成功的 channel
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + "---上线");
		group.writeAndFlush(channel.remoteAddress() + "---上线\n");

		// 将当前channel添加到group中
		group.add(channel);
	}

	/**
	 * 只要有客户端Channel与服务端断开连接就会执行这个方法
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 获取到当前要断开连接的Channel
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + "---下线");
		group.writeAndFlush(channel.remoteAddress() + "下线，当前在线人数：" + group.size() + "\n");

		// group 中存放的都是active状态channel 一旦某个channel不是active
		// group 会自动将其剔除，所以下面语句不需要执行
		// remove的执行场景是将active的 channel 移除
		//group.remove(channel);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx .close();
	}

}
