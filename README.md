# MNoute-config-center
配置中心

1. 划分明明空间的概念根据不同的明明空间获取不同的配置i
2. 用户权限管理，使用他做一个双向帮顶维护数据信息
3. 系统分为admin config sdk
 
 - admin 负责统一配制，作为一个数据
 - config 负责和admin进行交互，获取相关的配置信息
 - sdk 和config进行交互，真实的配置信息写入到这里
 
 
功能详细划分
 
1. 用户权限管理功能
/user/create?name=xxxx&email&passwod
/user/update/permissions?user_id&cluter&namespace&key
/user/create/permissions?user_id&cluter&namespace&key
/user/delete/permissions?user_id&cluter&namespace&key


    权限分配- 是否能够修改一个配置的信息
              是否能够查看一个配置的信息
              是否能够删除一个配置的信息
              是否能添加一个配置的信息
              
              是否能对一个命名空间下所有的配置进行那些操作
              是否能对一个集群下的所有配置进行那些操作
              
2. 配置功能
/config/create/namespace&cluter&key
    (1) 新建配置
    (2) 删除配置
    (3) 增加配置
    (4) 配置详情查看
    (5) 发布配置


3. 配置详情查看
    (1) 查看配置详情
    (2) 查看配置的状态

3. 数据统计功能
    集群数量
    配置数量

5. 命名空间管理功能
   (1) 新建命名空间
   (2) 删除命名空间
   (3) 修改命名空间
   (4) 按名称搜索命名空间
   
6. 集群管理
    (1) 新建新建集群
    (2) 删除集群空间
    (3) 修改集群空间
    (4) 按名称搜索命名空间

7. 操作历史记录

    (1) 历史查看
    (2) 回滚
    
模块划分
1. service,实用这个模块可以快速的实现所config请求的功能
2. config模块 config服务需要提供的功能
3. admin 提供所有的api的所拥有的功能
4. common 模块,通用工具某爱
5. user 用户信息管理模块
6. sdk 客户端获取配置所实用的配置包工具集合