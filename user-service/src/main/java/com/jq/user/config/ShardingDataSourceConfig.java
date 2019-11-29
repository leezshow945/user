package com.jq.user.config;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置数据源
 * @Auther: Lee
 * @Date: 2019/1/4 14:17
 */

@Configuration
@EnableConfigurationProperties(ShardingMasterSlaveConfig.class)
@ConditionalOnProperty({ "sharding.jdbc.data-sources.master.jdbc-url",
        "sharding.jdbc.master-slave-rule.master-data-source-name" })
public class ShardingDataSourceConfig {

    private final static Logger logger = LoggerFactory.getLogger(ShardingDataSourceConfig.class);

    @Autowired(required = false)
    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;

    /**
     * 配置数据源
     *
     * @return
     * @throws SQLException
     */
    @Bean("dataSource")
    public DataSource masterSlaveDataSource() throws SQLException {
        shardingMasterSlaveConfig.getDataSources().forEach((k, v) -> configDataSource(v));
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.putAll(shardingMasterSlaveConfig.getDataSources());
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap,
                shardingMasterSlaveConfig.getMasterSlaveRule(), new HashMap<>());
        logger.info("masterSlaveDataSource config complete！！");
        return dataSource;
    }

    /**
     * 可添加数据源一些配置信息
     *
     * @param dataSource
     */
    private void configDataSource(HikariDataSource dataSource) {

    }

}
