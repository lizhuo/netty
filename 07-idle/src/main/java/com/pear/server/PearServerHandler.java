package com.pear.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author lizhuo
 * @Description: 自定义服务端处理器
 * 需求：实现处理 C/S 模式的 socket 通信 demo
 * @date 2020-02-29 18:21
 */
public class PearServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 所有"规定之外"的事件可以通过此种方式触发
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String eventDesc = null;
			switch (event.state()) {
				case READER_IDLE:
					eventDesc = "读空闲超时";
					break;
				case WRITER_IDLE:
					eventDesc = "写空闲超时";
					break;
				case ALL_IDLE:
					eventDesc = "读写空闲超时";
					break;
			}
			System.out.println(eventDesc);
			// 空闲超时关闭连接
			ctx.close();
		} else {
			// 触发其他事件
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx .close();
	}

}
