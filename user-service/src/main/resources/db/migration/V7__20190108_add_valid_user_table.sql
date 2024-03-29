CREATE TABLE `valid_user_setting` (
  `site_id` bigint(20) NOT NULL COMMENT '站点id',
  `site_code` varchar(255) NOT NULL COMMENT '站点code',
  `recharge_amount` bigint(16) DEFAULT NULL COMMENT '充值金额',
  `withdraw_amount` bigint(16) DEFAULT NULL COMMENT '提现金额',
  `login_days` int(11) DEFAULT NULL COMMENT '登录天数',
  `login_count_num` int(11) DEFAULT NULL COMMENT '登录次数',
  `has_real_name` int(11) DEFAULT NULL COMMENT '是否填写真实姓名',
  `is_repeat` int(11) DEFAULT NULL COMMENT '真实姓名是否验证重复',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_by` varchar(255) NOT NULL COMMENT '创建者',
  `update_by` varchar(255) NOT NULL COMMENT '更新者',
  `valid_bet_amount` bigint(16) DEFAULT NULL COMMENT '有效投注金额',
  `valid_order_num` int(11) DEFAULT NULL COMMENT '有效订单量',
  `register_days` int(11) DEFAULT NULL COMMENT '注册天数',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='有效会员设置表';

CREATE TABLE `valid_user_statistics` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `site_id` bigint(20) NOT NULL COMMENT '站点id',
  `site_code` varchar(255) NOT NULL COMMENT '站点code',
  `recharge_amount` bigint(16) NOT NULL DEFAULT '0' COMMENT '充值金额',
  `withdraw_amount` bigint(16) NOT NULL DEFAULT '0' COMMENT '提现金额',
  `valid_bet_amount` bigint(16) NOT NULL DEFAULT '0' COMMENT '有效投注金额',
  `valid_order_num` int(11) NOT NULL DEFAULT '0' COMMENT '有效订单量',
  `register_days` int(11) NOT NULL DEFAULT '0' COMMENT '注册天数',
  `login_days` int(11) NOT NULL DEFAULT '0' COMMENT '登录天数',
  `login_count_num` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `has_real_name` int(11) NOT NULL DEFAULT '0' COMMENT '真实姓名',
  `is_repeat` int(11) NOT NULL DEFAULT '0' COMMENT '是否重复',
  `is_valid` int(11) NOT NULL DEFAULT '0' COMMENT '是否为有效会员',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `site_id` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='有效会员数据统计表';