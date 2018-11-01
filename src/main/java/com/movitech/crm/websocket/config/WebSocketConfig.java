package com.movitech.crm.websocket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by zhming on 2018/10/24.
 */

/**
 *
 * @Configuration:说明该类是配置类，等价于xml中的beans
 * @ConditionalOnClass:当类路径下有指定的类的条件,即存在MyHandler时初始化该配置类
 *
 */
@Configuration
@ConditionalOnClass(MyHandler.class)
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler(), "/myHandler").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
    }

    /**
     * @ConditionalOnMissingBean:缺失时,初始化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MyHandler.class)
    public MyHandler myHandler() {
        return new MyHandler();
    }
}
