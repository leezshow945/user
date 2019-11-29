package com.jq.user.score.service;

import com.jq.user.api.score.dto.UserRank;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserRankScoreEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreServiceTest {

    @Resource
    ScoreInnerService scoreInnerService;

    @Test
    public void testQueryUserRank() {
        List<UserRankEntity> list=this.scoreInnerService.queryUserRank(new UserRank());
        for(UserRankEntity u:list){
            System.out.println(u.getId());
            System.out.println(u.getRankName());
            for(UserRankScoreEntity ue:u.getUserRankScoreEntityList()){
                System.out.println(ue.getScoreName());
                System.out.println(ue.getScoreVal());
            }
        }
    }

}
