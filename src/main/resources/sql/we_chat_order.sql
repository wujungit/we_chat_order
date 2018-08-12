-- 设置数据库信息输入输出编码格式
set names utf8;
-- 取消外键约束
set foreign_key_checks = 0;

-- table structure for `product_category`
drop table if exists `product_category`;
create table `product_category` (
    `category_id` int not null auto_increment comment '类目ID',
    `category_name` varchar(64) not null comment '类目名字',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`category_id`)
)engine=innodb default charset=utf8 comment='类目表';

-- table structure for `product_info`
drop table if exists `product_info`;
create table `product_info` (
    `product_id` varchar(32) not null comment '商品ID',
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '单价',
    `product_stock` int not null comment '库存',
    `product_desc` varchar(64) comment '描述',
    `product_icon` varchar(512) comment '小图',
    `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态:0-正常,1-下架',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`product_id`)
)engine=innodb default charset=utf8 comment='商品表';

-- table structure for `order_master`
drop table if exists `order_master`;
create table `order_master` (
    `order_id` varchar(32) not null comment '订单ID',
    `buyer_name` varchar(32) not null comment '买家名字',
    `buyer_phone` varchar(32) not null comment '买家电话',
    `buyer_address` varchar(128) not null comment '买家地址',
    `buyer_openid` varchar(64) not null comment '买家微信openid',
    `order_amount` decimal(8,2) not null comment '订单总金额',
    `order_status` tinyint(3) not null default '0' comment '订单状态,默认新下单',
    `pay_status` tinyint(3) not null default '0' comment '支付状态,默认未支付',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
)engine=innodb default charset=utf8 comment='订单表';

-- table structure for `order_detail`
drop table if exists `order_detail`;
create table `order_detail` (
    `detail_id` varchar(32) not null comment '详情ID',
    `order_id` varchar(32) not null comment '订单ID',
    `product_id` varchar(32) not null comment '商品ID',
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '当前价格,单位分',
    `product_quantity` int not null comment '数量',
    `product_icon` varchar(512) comment '小图',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`)
)engine=innodb default charset=utf8 comment='订单详情表';

-- table structure for `seller_info`
drop table if exists `seller_info`;
create table `seller_info` (
    `user_id` varchar(32) not null comment '用户ID',
    `username` varchar(32) not null comment '用户名',
    `password` varchar(32) not null comment '密码',
    `openid` varchar(64) not null comment '微信openid',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`user_id`)
)engine=innodb default charset=utf8 comment '卖家信息表';

set foreign_key_checks = 1;
