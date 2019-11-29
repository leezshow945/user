package com.jq.user.score.api;

import com.jq.framework.core.result.ApiResult;
import com.jq.user.api.score.dto.UserRank;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.service.ScoreInnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreApi {
    private final static Logger logger = LoggerFactory.getLogger(ScoreApi.class);

    @Resource
    private ScoreInnerService scoreInnerService;


    /**
     * 根据查询条件查询用户等级模板
     * @author leeY
     */
    @RequestMapping(value = "queryUserRank", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<?> queryUserRank(UserRank userRank) {
        logger.info(String.format("查询等级模板"));
        List<UserRankEntity> userRankEntityList =scoreInnerService.queryUserRank(userRank);
        return new ApiResult<>(userRankEntityList);
    }




}
