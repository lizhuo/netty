package com.pear.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhuo
 * @Description: 自定义服务端处理器
 * 需求：实现处理 C/S 模式的 socket 通信 demo
 * @date 2020-02-29 18:21
 */
public class PearHandler extends SimpleChannelInboundHandler<String> {

	private int counter = 0;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// 客户端数据 输出至 console
		System.out.println("server receive msg no:[" + ++counter + "] data: " + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx .close();
	}

}
