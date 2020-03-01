package com.pear.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lizhuo
 * @Description: Channel Init
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
		ChannelPipeline pipeline = ch.pipeline();
		// 添加 Http 编解码器
		pipeline.addLast(new HttpServerCodec());
		// 添加大块数据 Chunk 处理器
		pipeline.addLast(new ChunkedWriteHandler());
		// 添加 Chunk 聚合处理器
		pipeline.addLast(new HttpObjectAggregator(4096));
		// 添加 WebSocket 协议转换处理器
		pipeline.addLast(new WebSocketServerProtocolHandler("/pear"));
		// 添加自定义处理器
		pipeline.addLast(new PearHandler());
	}

}
