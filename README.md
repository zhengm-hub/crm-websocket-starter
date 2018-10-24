### webSocket

1. 介绍
 
   > HTTP是一个应用层协议,无状态的,端口号为80.主要的版本有1.0/1.1/2.0.
   
     ```
     HTTP/1.* 一次请求-响应,建立一个连接,用完关闭;
     
     HTTP/1.1 串行化单线程处理,可以同时在同一个tcp链接上发送多个请求,但是只有响应是有顺序的,
     只有上一个请求完成后,下一个才能响应.一旦有任务处理超时等,后续任务只能被阻塞(线头阻塞);
     
     HTTP/2 并行执行.某任务耗时严重,不会影响到任务正常执行.
     ```
     
   > Websocket是html5提出的一个协议规范,是为解决客户端与服务端实时通信.
   
     ```
     本质上是一个基于tcp,先通过HTTP/HTTPS协议发起一条特殊的http请求进行握手后创建一个用于交换数据的TCP连接.
     WebSocket优势:浏览器和服务器只需要要做一个握手的动作,在建立连接之后,双方可以在任意时刻,相互推送信息.
     同时,服务器与客户端之间交换的头信息很小.
     ```
        
   > http和websocket的长连接区别
   
     ```
     HTTP1.1通过使用Connection:keep-alive进行长连接,HTTP 1.1默认进行持久连接.
     在一次TCP连接中可以完成多个HTTP请求,但是对每个请求仍然要单独发header,
     Keep-Alive不会永久保持连接,它有一个保持时间,可以在不同的服务器软件（如Apache）中设定这个时间.
     这种长连接是一种“伪链接”.
     websocket的长连接,是一个真的全双工.长连接第一次tcp链路建立之后,后续数据可以双方都进行发送,不需要发送请求头.     
     keep-alive双方并没有建立正真的连接会话,服务端可以在任何一次请求完成后关闭.
     WebSocket它本身就规定了是正真的、双工的长连接,两边都必须要维持住连接的状态.
     ```
     
2. spring-boot集成webSocket(以插件形式无侵入组合)

   > 创建自定义starter以便其他项目使用
   
   ```javascript
   1. //引入依赖
      // webSocket用到的spring-boot的starter
      <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-websocket</artifactId>
      </dependency>
      // 用来自定义starter自定配置的依赖
      <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-autoconfigure-processor</artifactId>
           <optional>true</optional>
      </dependency>
   
   2. //代码注解

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
              webSocketHandlerRegistry.addHandler(myHandler(), "/myHandler").addInterceptors(new WebSocketInterceptor());
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
   
   3. 配置spring.factories
      org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
      com.movitech.crm.websocket.config.WebSocketConfig
   
   4. //其他的应用程序只要引入该自定义start便可使用webSocket相关功能
      // eg:
      <dependency>
          <groupId>com.movitech.crm.websocket</groupId>
          <artifactId>crm-websocket-starter</artifactId>
          <version>1.0.0</version>
      </dependency>
   ```
   
     