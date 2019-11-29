package com.jq.user.log.dao;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.user.constant.UserConstant;
import com.jq.user.log.entity.LogUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogUserDaoTest {

    @Resource
    private LogUserDao logUserDao;

    @Test
    public void insertList() {
        List<LogUserEntity> list = new ArrayList<>();
        int num = 10;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            long id = IdWorker.getId();
            ids.add(id);
            LogUserEntity logUserEntity = new LogUserEntity();
            long loginId = IdWorker.getId();//
            long siteId = IdWorker.getId();

            logUserEntity.setId(id);
            logUserEntity.setPlat(1);
            logUserEntity.setUserName("test");
            logUserEntity.setContent("content");
            logUserEntity.setUserId(loginId);
            logUserEntity.setLoginIp("192.168.11.1");
            logUserEntity.setLoginTime(new Date());
            logUserEntity.setLoginArea("中国 武汉");
            logUserEntity.setIsDiffAreaLogin(1);
            logUserEntity.setType("LOGIN_SUCCESS");
            logUserEntity.setAccountType("SYS");
            logUserEntity.setSiteId(siteId);
            logUserEntity.setIsDel(UserConstant.IS_F);
            logUserEntity.setFlagType("操作日志");
//            logUserEntity.setLoginUrl("logUrl");
            logUserEntity.setCreateTime(new Date());
            logUserEntity.setUpdateTime(new Date());
            list.add(logUserEntity);
        }
        logUserDao.insertList(list);
        List<LogUserEntity> list1 = logUserDao.selectBatchIds(ids);
        assert list1.size() == num;
    }


}
