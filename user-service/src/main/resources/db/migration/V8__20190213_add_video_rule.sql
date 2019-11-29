CREATE TABLE `rebate_video_rule` (
  `id` bigint(20) NOT NULL,
  `rule_info_id` bigint(20) NOT NULL,
  `game_code` varchar(50) NOT NULL COMMENT '真人游戏编码',
  `game_name` varchar(50) NOT NULL COMMENT '真人游戏名称',
  `game_rebate` bigint(20) NOT NULL DEFAULT '0' COMMENT '真人游戏返点',
  PRIMARY KEY (`id`),
  KEY `rule_info_id` (`rule_info_id`),
  KEY `game_code` (`game_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='真人游戏返水规则表';

ALTER TABLE rebate_rule DROP COLUMN rule_type;