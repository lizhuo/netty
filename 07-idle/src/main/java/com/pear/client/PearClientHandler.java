package com.pear.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhuo
 * @Description: 客户端处理类
 * SimpleChannelInboundHandler 会释放 msg
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
		System.out.println(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
