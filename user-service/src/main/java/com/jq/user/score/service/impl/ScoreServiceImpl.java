package com.jq.user.score.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jq.user.api.score.dto.UserRank;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dao.UserRankScoreDao;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserRankScoreEntity;
import com.jq.user.score.service.ScoreInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class ScoreServiceImpl implements ScoreInnerService {

    @Resource
    UserRankDao userRankDao;
    @Resource
    UserRankScoreDao userRankScoreDao;


    @Override
    @Transactional(readOnly = true)
    public List<UserRankEntity> queryUserRank(UserRank userRank){

        //根据请求参数查询等级模板数据
        EntityWrapper ew =new EntityWrapper<UserRankEntity>();
        ew.eq("is_del","F");
        if(userRank.getSiteId()!=null){
            ew.eq("site_id",userRank.getSiteId());
        }
        if(StrUtil.isNotEmpty(userRank.getRankLevel())){
            ew.eq("rank_level",userRank.getRankLevel());
        }
        if(StrUtil.isNotEmpty(userRank.getRankName())){
            ew.eq("rank_name",userRank.getRankName());
        }
        if(StrUtil.isNotEmpty(userRank.getTimeStr())){
            ew.eq("subString(create_time,1,10)",userRank.getTimeStr());
        }
        if(userRank.getMaxScore()!=null){
            ew.eq("max_score",userRank.getMaxScore());
        }
        List<UserRankEntity> UserRankEntitys=userRankDao.selectList(ew);

        //查询所有等级模板的积分详情
        for (UserRankEntity userRankEntity : UserRankEntitys) {
            List<UserRankScoreEntity> thisScores = userRankScoreDao.selectList(
                    new EntityWrapper<UserRankScoreEntity>().eq("rank_id",userRankEntity.getId())
            );
            userRankEntity.setUserRankScoreEntityList(thisScores);
        }
        return UserRankEntitys;
    }

}