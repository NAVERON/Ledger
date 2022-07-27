
# About  

Ledger Cloud Service Provider  

记账软件服务端后台  


### 技术栈(计划)  

- Spring Boot for Back-End Implements  
- Postgresql, 代替mysql  
- Redis, Zookeeper, Kafka等中间件  
- 配置springboot 使用ubderflow 新的web容器  

## 学习记录  

1. 使用shiro完成身份授权和认证部分  
2. 连接postgresql pgcli -h localhost -p 5432 -U postgres  
使用uri的格式 : pgcli postgres://postgres:wangyulong@113.31.119.92:5432/ledger  
3. spring mvc web 过滤器 拦截器顺序, 先进入过滤器逻辑,再进入拦截器逻辑  
4. 当前发现的一些问题 : 角色管理需要设置单独的可操作接口和服务, 当前使用enum固定的几个角色, 权限的定义比较模糊  
5. 一般情况下, entity设置默认值 防止null的出现  
6. controller层做参数校验 条件判断 , service 做二次检查, 比如null, dao层只接受参数完全合规范的对象, 这样分工明确有助于避免各层次多次重复判断,增加工作量  
7. 注册激活验证码如何保证大量用户注册的时候 验证码一定不一样, 当然现在很大程度上是不一样的, 但是是否有数学理论证明并生成随机字符串一定不一样? 比如加入时间参数  
8. JPA 扫描引用项目的Entity 需要使用JPaRepositoryScan和EntityScan 注解, 指明扫描路径  
9. 总体的权限设计上没有综合考虑, permissions 设计的语义不明确, permission对应的值是表示http 协议方法-> GET/POST 还是针对资源的某种限制,需要一个具体的逻辑解释, 这里不明确,造成了空缺  
10. 当前仅仅对项目做了模块剥离, 并没有真正的实现'分布式', 如果后期再有项目, 可以考虑实现基于rpc框架的  
11. 当前返回的错误码没有统一的标准除了200正常状态外, 其余都是随心设置, 后期应当在ResponseEntity 上包装一层返回对象, 对象封装返回的对象并json化,这样可以统一格式  
12. 








