package com.pear.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhuo
 * @Description: 自定义服务端处理器
 * 需求：实现处理 C/S 模式的 socket 通信 demo
 * @date 2020-02-29 18:21
 */
public class PearHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 客户端数据 输出至 console
		System.out.println("from client " + ctx.channel().remoteAddress() + " msg: " + msg);

		// 发送给客户端数据
		ctx.writeAndFlush("from server: " + UUID.randomUUID());
		TimeUnit.MILLISECONDS.sleep(500);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx .close();
	}

}
