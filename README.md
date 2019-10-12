# MNoute-config-center
配置中心

1. 划分明明空间的概念根据不同的明明空间获取不同的配置i
2. 用户权限管理，使用他做一个双向帮顶维护数据信息
3. 系统分为admin config sdk
 
 - admin 负责统一配制，作为一个数据
 - config 负责和admin进行交互，获取相关的配置信息
 - sdk 和config进行交互，真实的配置信息写入到这里
 
 4. 