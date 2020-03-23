# microservice

## springcloud微服务
注册服务中心使用nacos
### 项目目录结构
* common: 公共服务中心。包括对redis rabbitmq 公共配置及访问类和工具包、异常类等
* sysadmin: admin后台管理项目。主要提供web端接口的访问
* auth: 权限控制中心。主要包括authorization-service和authorization-resource两个module,分别为授权服务中心和资源管理中心，采用spring security oauth2，使用jwt作为token验证。