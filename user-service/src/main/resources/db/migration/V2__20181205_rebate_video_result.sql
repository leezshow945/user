CREATE TABLE `rebate_video_config` (
  `id` bigint(20) NOT NULL,
  `rule_id` bigint(20) NOT NULL COMMENT '规则组id',
  `rebate_most` bigint(16) DEFAULT '0' COMMENT '返水上限',
  `effective_bets` bigint(16) DEFAULT '0' COMMENT '总有效投注',
  PRIMARY KEY (`id`),
  KEY `rule_id` (`rule_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='真人投注与上限配置表';


CREATE TABLE `rebate_video_config_info` (
  `id` bigint(20) NOT NULL,
  `config_id` bigint(20) NOT NULL COMMENT '真人投注上限表id',
  `game_code` varchar(50) NOT NULL COMMENT '真人游戏code',
  `game_name` varchar(100) NOT NULL COMMENT '真人游戏名称',
  `rebate_ratio` bigint(20) DEFAULT '0' COMMENT '真人游戏返点',
  PRIMARY KEY (`id`),
  KEY `config_id` (`config_id`) USING BTREE,
  KEY `game_code` (`game_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='真人返水配置详情表';

CREATE TABLE `rebate_video_result_info` (
  `id` bigint(20) NOT NULL,
  `result_id` bigint(20) NOT NULL COMMENT '返水结果id',
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `high_level_account` varchar(70) NOT NULL,
  `effective_bets` bigint(20) DEFAULT '0' COMMENT '总有效投注',
  `status` tinyint(4) DEFAULT '0' COMMENT '明细状态(0:已处理;1:已冲销)',
  `all_rebates` bigint(20) DEFAULT '0' COMMENT '返水总金额',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `result_id` (`result_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='真人返水结果详情表';

CREATE TABLE `rebate_video_game` (
  `id` bigint(20) NOT NULL,
  `result_info_id` bigint(20) NOT NULL COMMENT '返水结果详情id',
  `game_code` varchar(50) NOT NULL COMMENT '真人游戏编码',
  `game_name` varchar(50) NOT NULL COMMENT '真人游戏名称',
  `game_bet` bigint(20) DEFAULT '0' COMMENT '真人游戏总投注',
  `game_rebate` bigint(20) DEFAULT '0' COMMENT '真人游戏总返水',
  PRIMARY KEY (`id`),
  KEY `result_info_id` (`result_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='真人返水结果 游戏详情表';

alter table rebate_rule add rule_type int(11) DEFAULT '0' COMMENT '返水规则组类型标识 0彩票1真人视讯';
alter table rebate_result add result_type int(11) DEFAULT '0' COMMENT '返水结果类型标识 0彩票1真人视讯';






