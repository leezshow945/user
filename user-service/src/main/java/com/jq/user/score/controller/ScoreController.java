package com.jq.user.score.controller;

import com.jq.user.api.score.api.ScoreService;
import com.jq.user.score.service.ScoreInnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class ScoreController implements ScoreService {

    private final static Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Resource
    ScoreInnerService scoreInnerService;

//    @Override
//    public List<UserRank> getRanksBySiteId(@RequestBody String siteId){
//        return scoreInnerService.getRanksBySiteId(siteId);
//    }

}
