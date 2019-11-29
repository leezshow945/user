package com.jq.user.score.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.score.dao.UserSignDao;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.entity.UserSignEntity;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Lee
 * @Date: 2018/11/12 15:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RankBonusTest {
    @Resource
    private RankBonusService rankBonusService;
    @Resource
    private UserRankScoreInnerService userRankScoreInnerService;
    @Resource
    UserSignDao userSignDao;

    @Test
    public void queryTest() {
        ApiResult<RankBonusConfigDTO> apiResult = rankBonusService.getRankBonus(1071079546521452546l, null);
        assert apiResult.getCode() == 10000;
    }


    @Test
    public void updateTest() {
        RankBonusConfigDTO dto = new RankBonusConfigDTO();
        dto.setSiteId(5000l);
        dto.setSiteCode("test2");
        dto.setBonusEndTime("21:00");
        dto.setBonusStartTime("00:20");
        dto.setLevel1Bet(100000l);
        dto.setLevel2Bet(200000l);
        dto.setLevel3Bet(300000l);
        dto.setUpgradeRatio(6000);
        dto.setUpdateBy("lee2");
        dto.setUpdateTime(new Date());


        ApiResult apiResult = rankBonusService.updateRankBonus(dto);
        assert apiResult.getCode() == 10000;

    }

    @Test
    public void getUserRankBonusTest() {
        ApiResult<UserRankBonusDTO> userRankBonus = rankBonusService.getUserRankBonus(1040519427137794050l);
        System.out.println(userRankBonus.toString());
    }

    @Test
    public void mybatisPlus3Test() {
        List<UserSignEntity> userSignEntities = userSignDao.selectList(new QueryWrapper<UserSignEntity>()
                .eq("site_id", 1040499894897405953l)
                .eq("user_id", 1042609512440197122l)
                .apply("DATE_FORMAT(sign_time, '%Y%m' )= DATE_FORMAT(CURDATE( ), '%Y%m')"));
        System.out.println(userSignEntities.size());
    }

}
