package com.jq.user.util;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilTest {

    @Test
    public void dateUtilTest() {
        // 2018-04-28 10:51:53
        String time1 = DateUtil.formatDateTime(new Date());
        // 2018-04-28
        String time2 = DateUtil.formatDate(new Date());
        String time3 = DateUtil.formatBetween(new Date().getTime());
        int i = DateUtil.ageOfNow(new Date());
        int second = DateUtil.second(new Date());
        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
        System.out.println(i);
        System.out.println(second);
    }
}
