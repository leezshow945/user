CREATE INDEX user_id ON user_bank (user_id);
CREATE INDEX card_no ON user_bank (card_no);
CREATE INDEX card_user_name ON user_bank (card_user_name);

CREATE INDEX type ON log_user (type);
CREATE INDEX flag_type ON log_user (flag_type);

CREATE INDEX real_name ON user_info (real_name);
CREATE INDEX mobile ON user_info (mobile);
CREATE INDEX email ON user_info (email);

CREATE INDEX user_id ON user_favorite (user_id);
CREATE INDEX game_code ON user_favorite (game_code);

CREATE INDEX ban_rebate_time ON user_rebate (ban_rebate_time);
DROP INDEX high_level_account ON user_rebate;
DROP INDEX high_level_id ON user_rebate;
DROP INDEX path ON user_rebate;