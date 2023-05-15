# HJ212 Protocol
> 一个实现了 **污染物在线监控（监测）系统数据传输标准** 编解码的工具.
>
> 详情查看国家生态环境部官网 [污染物在线监控（监测）系统数据传输标准](https://www.mee.gov.cn/ywgz/fgbz/bz/bzwb/other/qt/201706/t20170608_415697.shtml)

## 目录结构
```
|-- hj212-protocol
    |-- docs                                HJ212协议官方文档
    |-- example                             示例代码(用于接收消息的Netty服务器)
    |-- protocol                            协议解析部分
```
## 示例说明
启动类: com.github.guocay.hj212.MonitorCenterApplication.java

核心处理类: com.github.guocay.hj212.business.service.MonitorService.java

## 示例处理流程
1. 启动一个Spring应用, 通过扩展CommandLineRunner接口中启动了一个Netty服务器;
2. 启动Netty后, 会在上下文中挂一个Handler(com.github.guocay.hj212.netty.NettyServerHandler.java);
3. 在Handler的 "ChannelRead()" 中, 通过 MonitorService 处理解析数据并存库;
4. 数据会先存在竖表(MonitorFactorPo.java)里, 再通过 Hj212TransTask.java 的定时任务转存为 横表;
5. 接收或转换时的异常数据会存储在 MonitorErrorPo.java 所对应的表中;
