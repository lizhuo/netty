package com.pear.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @author lizhuo
 * @Description: 客户端处理类
 * SimpleChannelInboundHandler 会释放 msg
 * @date 2020-02-29 18:56
 */
public class PearClientHandler extends ChannelInboundHandlerAdapter {

	private String message = "Hello netty world. ";

	/**
	 * 当 Channel 被激活 会触发该方法执行
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		byte[] bytes = this.message.getBytes();

		ByteBuf buffer = null;

		for (int i=0; i<100; i++) {
			// 申请缓存空间
			buffer = Unpooled.buffer(bytes.length);
			// 写入缓存
			buffer.writeBytes(bytes);
			ctx.writeAndFlush(buffer);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
