package com.jq.user.api.log.api;

import com.jq.framework.core.feign.RedditFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "user", url = "${feign.user.url}", configuration = RedditFeignConfiguration.class)
public interface LogService {
}
