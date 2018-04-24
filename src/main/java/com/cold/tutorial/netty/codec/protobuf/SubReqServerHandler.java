package com.cold.tutorial.netty.codec.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hui.liao
 *         2016/2/18 10:29
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq subscribeReq = (SubscribeReqProto.SubscribeReq) msg;

        if ("li".equalsIgnoreCase(subscribeReq.getUserName())) {
            System.out.println("receive subscribeReq :" + subscribeReq.toString());
            ctx.writeAndFlush(resp(subscribeReq.getSubReqId()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqId) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        SubscribeRespProto.SubscribeResp resp = builder.setSubReqId(subReqId).setRespCode(001).setDesc("desc").build();
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
