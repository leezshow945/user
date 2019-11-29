alter table rebate_result add all_bets bigint(20) DEFAULT '0' COMMENT '真人视讯返水总投注额';
alter table rebate_video_result_info modify column high_level_account varchar (70) DEFAULT null COMMENT '直属上级用户名';
