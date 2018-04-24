package com.cold.tutorial.netty.codec.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/2/18 10:46
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        IntStream.range(0, 10).forEach(i -> ctx.write(subReq(i)));
        ctx.flush();
    }

    private Object subReq(int index) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(index).setUserName("li").setProductName("product1");
//        ArrayList<String> addressList = new ArrayList<>();
//        addressList.add("address1");
//        addressList.add("address2");
//        addressList.add("address3");
        builder.setAddress("address1");

        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive resp : " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
