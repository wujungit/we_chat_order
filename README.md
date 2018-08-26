# we_chat_order
1、分布式系统的特点
多节点，消息通讯，不共享内存
分布式系统，集群，分布式计算
分布式系统中各个节点是通过发送消息来通讯的，比如http/rest接口/RPC
2、session
广义的session
理解为一种保存key-value的机制
回话有两种常规的方式，sessionId/token
3、synchronized
是一种解决方法，无法做到细粒度控制，只适合单点的情况
redis分布式锁
4、redis缓存
命中/失效/更新
