# log接入方式
## log4j RocketMQAppender
#### Log4j由三个重要的组建构成：
1. 日志信息的优先级
2. 日志信息的输出目的地
3. 日志信息的输出格式

日志优先级从高到低有FATAL、ERROR、WARN、INFO、DEBUG、TRACE，用来指定这条日志信息的重要程度；日志信息的输出目的地指定了日志输出的途径，是打印到控制台或者文件中或者通过JMS进行传输；输出格式则控制了日志信息的显示内容。

使用 RocketMQAppender，可以控制日志的输出目的地为RocketMQ, 输出的格式为JSON格式，日志的样式如下：
```
{
    "level": "ERROR",
    "location": "com.xxx.Demo.main(Demo.java:6)",
    "message": "this is a test",
    "thread": "main",
    "time": "2016-07-21 03:15:00"
}
```
level是日志级别，location是日志打印语句的代码位置，message是日志内容，thread是线程名称，time是日志打印时间

RocketMQAppender中会把用户的日志通过队列异步传输，提高吞吐量

### 使用方法
1. maven中引入依赖
```
<dependency>
    <groupId>com.xxx.message</groupId>
    <artifactId>log-api</artifactId>
    <version>0.1</version>
</dependency>
```
2. 修改log4j.properties文件
配置logger
```
log4j.logger.rocketMQLog=[level],appenderName
```
其中level是日志记录的优先级，通过在这定义的级别，可以控制到应用程序中相应级别的日志信息的开关，比如这边定义的info级别，那么应用程序中所有debug级别的日志信息将不被打印出来。
appenderName就是指定日志信息输出到哪个地方，appenderName会对应到具体某一种appender类型，每种appender都会提供一些配置参数。
使用RocketMQAppender配置如下
```
log4j.logger.rocketMQLog=info,rocket_mq
log4j.appender.rocket_mq=com.xxx.message.log.log4j.RocketMQAppender
log4j.appender.rocket_mq.namesrvAddr=[127.0.0.1:9876]
log4j.appender.rocket_mq.projectName=[日志服务的project名称]（可选，默认为logs）
```

通过这样的设置，那么只有logger名称为 `rocketMQLog`的才会通过RocketMQAppender进行传输

### appender配置参数

RocketMQAppender可供选择的参数如下：可选参数在不填的情况下，使用默认值
```
#RocketMQ name server地址，必选
log4j.appender.rocket_mq.namesrvAddr=[127.0.0.1:9876]
#日志服务的project名称，建议填写，用来设置RocketMQ的tags，标识消息子类型，主要用来区别业务类型,可选参数
log4j.appender.rocket_mq.projectName=logs
#多少个线程同时发送日志，如果日志多的话，建议加大，可选参数
log4j.appender.rocket_mq.senderPoolSize=2
#客户端限制的消息大小,可选参数
log4j.appender.rocket_mq.maxMessageSize=1024 * 128
#消息发送失败时重试次数，可选参数
log4j.appender.rocket_mq.maxSenderRetries=3
#输出到日志服务的时间格式，使用java中SimpleDateFormat格式化时间，可选参数
log4j.appender.rocket_mq.timeFormat=yyyy-MM-dd HH:mm:ss
#时间的时区，可选参数
log4j.appender.rocket_mq.timeZone=UTC
```





