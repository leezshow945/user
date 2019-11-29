package com.jq.user.score.dao;

import com.jq.user.api.score.dto.UserRank;
import com.jq.user.score.entity.UserRankEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreDaoTest {

    @Resource
    UserRankDao userRankDao;

    @Test
    public void testNewBean() {
        UserRankEntity userRankEntity = new UserRankEntity();
        userRankEntity.setMaxScore(0);
        userRankEntity.setRankLevel("0");
        userRankEntity.setRankName("普通用户");
        userRankEntity.setSiteId(888L);
        userRankEntity.setCreateTime(new Date());
        userRankEntity.setCreateBy(11L);
        userRankEntity.setUpdateTime(new Date());
        userRankEntity.setUpdateBy(0L);

        this.userRankDao.insert(userRankEntity);

    }

}
