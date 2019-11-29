package com.jq.user.api.score.api;

import com.jq.framework.core.feign.RedditFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "user", url = "${feign.user.url}", configuration = RedditFeignConfiguration.class)
public interface ScoreService {

//    //根据站点名获取该站点所有等级模板
//    @RequestMapping(value = "/inner/queryUserRank", method = RequestMethod.GET)
//    List<UserRank> getRanksBySiteId(@RequestBody String siteId);

}
