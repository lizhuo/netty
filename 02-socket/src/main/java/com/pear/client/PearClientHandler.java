package com.pear.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhuo
 * @Description: 客户端处理类
 *
 * @date 2020-02-29 18:56
 */
public class PearClientHandler extends SimpleChannelInboundHandler<String> {

	/**
	 * 读取来自服务端的消息
	 * @param ctx
	 * @param msg 与范型类型相同
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// 服务端数据 输出至 console
		System.out.println("from server " + ctx.channel().remoteAddress() + " msg: " + msg);
		ctx.channel().writeAndFlush("from client " + LocalDateTime.now());
 		TimeUnit.MILLISECONDS.sleep(500);
	}

	/**
	 * 当 Channel 被激活 会触发该方法执行
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush("from client begin talking");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
