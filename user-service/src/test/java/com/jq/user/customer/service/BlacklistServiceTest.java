package com.jq.user.customer.service;


import com.jq.user.customer.entity.BlacklistEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlacklistServiceTest {

    @Resource
    private BlacklistInnerService blacklistInnerService;

    /**
     * Author: Lee
     * Description: 测试黑名单
     * Date: 2018/5/23
     */
    @Test
    public void blacklistTest() {
        BlacklistEntity blacklistEntity =new BlacklistEntity();
        blacklistEntity.setId(983263789425176578l);
        blacklistEntity.setUserName("test");
        Boolean update = blacklistInnerService.update(blacklistEntity);
        System.out.println(update);
    }

}
