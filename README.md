# microservice

## springcloud微服务

用到的组件有：

* 注册中心、配置中心 Nacos
* 限流 Sentinel
* 分布式事务 Seata
* 网关 SpringCloud Gateway
* 认证授权 Spring Cloud Oauth2
* 服务调用 Feign
* docker、docker-compose
### 项目目录结构
* common: 公共服务中心。包括对redis rabbitmq 公共配置及访问类和工具包、异常类等
* sysadmin: admin后台管理项目。主要提供web端接口的访问
* auth: 权限控制中心。主要包括authorization-service和authorization-resource两个module,分别为授权服务中心和资源管理中心，采用spring security oauth2，使用jwt作为token验证。
认证服务端
提供认证、授权服务
实现方式，主要复写AuthorizationServerConfigurerAdapter实现

认证服务1-对称加密方式
对称加密，表示认证服务端和认证客户端的共用一个密钥

实现方式
AccessToken转换器-定义token的生成方式，这里使用JWT生成token，对称加密只需要加入key等其他信息（自定义）。




