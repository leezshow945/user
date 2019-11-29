DROP TABLE rebate_video_config;
DROP TABLE rebate_video_config_info;
DROP TABLE rebate_video_game;
DROP TABLE rebate_video_result_info;

ALTER TABLE rebate_result DROP COLUMN result_type;
ALTER TABLE rebate_rule_info ADD INDEX `effective_bets` (`effective_bets`) USING BTREE ;

CREATE TABLE `rebate_video_result_info` (
  `id` bigint(20) NOT NULL,
  `result_info_id` bigint(20) NOT NULL,
  `game_code` varchar(50) NOT NULL COMMENT '真人游戏编码',
  `game_name` varchar(50) NOT NULL COMMENT '真人游戏名称',
  `game_bet` bigint(20) NOT NULL DEFAULT '0' COMMENT '真人游戏投注金额',
  `game_rebate` bigint(20) NOT NULL DEFAULT '0' COMMENT '真人游戏返点',
  PRIMARY KEY (`id`),
  KEY `result_info_id` (`result_info_id`),
  KEY `game_code` (`game_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='真人游戏返水结果表';