package com.jq.user.config;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放HikariDataSource数据源
 * @Auther: Lee
 * @Date: 2019/1/4 14:11
 */
@Data
@ConfigurationProperties(prefix = "sharding.jdbc")
public class ShardingMasterSlaveConfig {
    private Map<String, HikariDataSource> dataSources = new HashMap<>();
    private MasterSlaveRuleConfiguration masterSlaveRule;
}
