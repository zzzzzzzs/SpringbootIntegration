/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package me.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RxtxClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf o) throws Exception {

        byte[] req = new byte[o.readableBytes()];
        // 从里面读取数据，这里面的数据是16进制的，0x..
        o.readBytes(req);

        log.info(req.toString());

//         将hex解码成String
//        System.out.println(new String(req));
//        // 十六进制转换字符串，没有解码
//        String str = HexUtil.encodeHexStr(req);
//        System.out.println(str);
    }
}