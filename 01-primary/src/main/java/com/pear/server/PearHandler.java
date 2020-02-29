package com.pear.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lizhuo
 * @Description: 自定义服务端处理器
 * 需求：用户提交请求后 浏览器会出现 Hello Netty World
 * @date 2020-02-29 16:14
 */
public class PearHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 当 Channel 中有来自客户端的数据时 就会触发该方法执行
	 * @param ctx 上下文
	 * @param msg 来自 Channel（客户端）中的数据
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("msg: " + msg.getClass());
		System.out.println("ctx.remoteIp: " + ctx.channel().remoteAddress() );
	}

	/**
	 * 当 Channel 中数据在处理过程中发生异常时 触发执行
	 * @param ctx 上下文
	 * @param cause 发生的异常对象
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// close channel
		ctx.channel().close();
	}

}
