# spring-cloud-blueprint
基于Spring-cloud的微服务脚手架

本项目基于https://github.com/paascloud/paascloud-master学习改造,仅用于学习交流之用


- infras
基础设施层.用于提供服务治理, 指标监控,网关路由,统一配置中心等

- web
Web应用前端,为各web网站. 例如官网, 运营管理网站等
- apis
接口层,对前端提供业务接口
- business
业务服务层,用于垂直业务整合
- services
垂直业务层,用于维护自有数据,并对外提供数据的访问
- eventdriven
事件驱动一致性
- common
共享库
