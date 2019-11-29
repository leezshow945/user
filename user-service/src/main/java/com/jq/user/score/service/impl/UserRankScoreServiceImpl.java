package com.jq.user.score.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.constant.UserConstant;
import com.jq.user.score.dao.UserRankScoreDao;
import com.jq.user.score.entity.UserRankScoreEntity;
import com.jq.user.score.service.UserRankScoreInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class UserRankScoreServiceImpl implements UserRankScoreInnerService {

    @Resource
    private UserRankScoreDao userRankScoreDao;


    @Override
    @Transactional(readOnly = true)
    public List<UserRankScoreEntity> findByRankId(Long rankId) {
        QueryWrapper<UserRankScoreEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("rank_id", rankId);
        ew.ne("score_code", "COMPLETE_USER");
        ew.ne("score_code", "DEPOSIT");
        ew.ne("score_code", "EXCHANGE");
        ew.ne("score_code", "TACK_OUT");
        ew.ne("score_code", "GET_TO_RANK");
        return userRankScoreDao.selectList(ew);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkScoreType(Long rankId, String scoreType) {
        UserRankScoreEntity entity = userRankScoreDao.selectOne(new QueryWrapper<UserRankScoreEntity>()
                .eq("is_del",UserConstant.IS_F)
                .eq("is_enable",UserConstant.IS_T)
                .eq("rank_id",rankId)
                .eq("score_code",scoreType)
                .gt("score_val",0));
        return ObjectUtil.isNotNull(entity)?entity.getScoreVal():0;
    }


}
