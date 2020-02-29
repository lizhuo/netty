package com.pear.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author lizhuo
 * @Description: Chinnel Init
 * 此种方式 本类实例 在 pipeline 初始化后会被 GC ？
 * @date 2020-02-29 15:49
 */
public class PearChannelInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * channel 创建后触发 初始化channel
	 * @param ch
	 * @throws Exception
	 */
	protected void initChannel(SocketChannel ch) throws Exception {
		// 从 channel 中获取 pipeline
		ChannelPipeline pipeline = ch.pipeline();
		// 将 HttpServerCodec 放入pipeline
		// HttpServerCodec is A combination of HttpRequestDecoder and HttpResponseEncoder
		// HttpRequestDecoder 将 channel 中 ByteBuf 解码为 HttpRequest
		// HttpResponseEncoder 将 channel 中 HttpResponse 编码为 ByteBuf
		pipeline.addLast(new HttpServerCodec());
		// 自定义
		pipeline.addLast(new PearHandler());
	}

}
