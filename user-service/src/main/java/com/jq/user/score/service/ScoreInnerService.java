package com.jq.user.score.service;

import com.jq.user.api.score.api.ScoreService;
import com.jq.user.api.score.dto.UserRank;
import com.jq.user.score.entity.UserRankEntity;
import java.util.List;

public interface ScoreInnerService extends ScoreService {

    List<UserRankEntity> queryUserRank(UserRank userRank);

}