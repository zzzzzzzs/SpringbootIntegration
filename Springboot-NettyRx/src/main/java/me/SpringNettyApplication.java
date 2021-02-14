package me;

import io.netty.channel.ChannelFuture;
import me.server.RxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringNettyApplication implements CommandLineRunner {


    @Autowired
    private RxServer rxServer;

    public static void main(String[] args) {
        SpringApplication.run(SpringNettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = rxServer.run();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                rxServer.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
