# spring-cloud-blueprint
基于Spring-cloud的微服务脚手架

本项目基于 https://github.com/paascloud/paascloud-master 学习改造,仅用于学习交流之用


- infras
基础设施层.用于提供服务治理, 指标监控,网关路由,统一配置中心等

- web
Web应用前端,为各web网站. 例如官网, 运营管理网站等
- service-inter-apis
接口层,对前端提供业务接口
- business
业务服务层,用于垂直业务整合
- services
垂直业务层,用于维护自有数据,并对外提供数据的访问
- elastic-job-lite
分布式作业调试
- common
共享库
- config
配置中心配置文件


准备工作:
1. 针对各服务:建立数据库
2. 针对Docker:
    创建app_net新网络
    构建RocketMQ镜像

