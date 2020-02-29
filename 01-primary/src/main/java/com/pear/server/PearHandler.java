package com.pear.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

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
		//System.out.println("msg: " + msg.getClass());
		//System.out.println("ctx.remoteIp: " + ctx.channel().remoteAddress() );

		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			System.out.println("request method: " + request.method().name());
			System.out.println("request uri: " + request.uri() );

			if ("/favicon.ico".equals(request.uri())) {
				System.out.println("ignore favicon request");
				return;
			}

			// 构造响应体
			ByteBuf body = Unpooled.copiedBuffer("Hello Netty World", CharsetUtil.UTF_8);
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);

			// 初始化 response 头部
			HttpHeaders headers = response.headers();
			headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

			// 将响应对象写入 Channel
			//ctx.write(response);
			//ctx.flush();
			ctx.writeAndFlush(response)
					// 添加监听器 响应体发送完毕 则直接 关闭Channel
					.addListener(ChannelFutureListener.CLOSE);
		}
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
