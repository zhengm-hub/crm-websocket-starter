//package com.movitech.crm.websocket.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by zhming on 2018/10/24.
// */
//
///**
// * @Configuration:说明该类是配置类，等价于xml中的beans
// * @ConditionalOnClass:当类路径下有指定的类的条件,即存在WebSocketConfig时初始化该配置类
// */
//@Configuration
//@ConditionalOnClass(WebSocketConfig.class)
//public class CustomAutoConfiguration {
//
//    /**
//     * 缺失时,初始化
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(WebSocketConfig.class)
//    public WebSocketConfig webSocketConfig() {
//        return new WebSocketConfig();
//    }
//}
