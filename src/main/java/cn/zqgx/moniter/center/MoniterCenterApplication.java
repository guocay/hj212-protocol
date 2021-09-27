package cn.zqgx.moniter.center;

import cn.zqgx.moniter.center.server.TcpServer;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
@SpringBootApplication
@MapperScan("cn.zqgx.moniter.center.server.portal")
@EnableAutoConfiguration(exclude = {DruidDataSourceAutoConfigure.class})
public class MoniterCenterApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private TcpServer server;

    public static void main(String[] args) {
        SpringApplication.run(MoniterCenterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = server.start(url,port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}
