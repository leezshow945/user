ALTER TABLE user_rebate
  MODIFY flc_rebate BIGINT(20) DEFAULT '0'
  COMMENT '棋牌返点，单位：万';
ALTER TABLE rebate_result_info
  MODIFY flc_bets BIGINT(20) DEFAULT '0'
  COMMENT '棋牌总投注';
ALTER TABLE rebate_rule_info
  MODIFY flc_rebate BIGINT(20) DEFAULT '0'
  COMMENT '棋牌返点';
ALTER TABLE user_refer
  MODIFY flc_rebate BIGINT(20) DEFAULT '0'
  COMMENT '棋牌返点';
ALTER TABLE user_bonus_main
  MODIFY COLUMN play_type VARCHAR(2000);