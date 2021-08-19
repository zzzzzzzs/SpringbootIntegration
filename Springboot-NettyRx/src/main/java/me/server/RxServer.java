package me.server;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import me.handler.RxtxClientHandler;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RxServer {

    static OioEventLoopGroup group = new OioEventLoopGroup();
    private static RxtxChannel channel;

    public ChannelFuture run(){
        ChannelFuture future = null;
        var bootstrap = new Bootstrap();
        try {
            bootstrap.group(group).channelFactory(new ChannelFactory<RxtxChannel>() {
                public RxtxChannel newChannel() {
                    return channel;
                }
            })
                    .handler(new ChannelInitializer<RxtxChannel>() {
                        @Override
                        protected void initChannel(RxtxChannel ch) throws Exception {
                            ch.pipeline().addLast(
    //                                new LineBasedFrameDecoder(32768),
    //                                new StringEncoder(),
    //                                new StringDecoder(),
                                    new RxtxClientHandler()
                            );

                        }
                    });
            channel = new RxtxChannel();
            channel.config().setBaudrate(9600)
                    .setDatabits(RxtxChannelConfig.Databits.DATABITS_8)
                    .setParitybit(RxtxChannelConfig.Paritybit.NONE)
                    .setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
            future = bootstrap.connect(new RxtxDeviceAddress("COM3")).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return future;
    }

    public void destroy() {
        log.info("Shutdown Netty rx...");
        if(channel != null) { channel.close();}
        group.shutdownGracefully();
        log.info("Shutdown Netty rx Success!");
    }

}
