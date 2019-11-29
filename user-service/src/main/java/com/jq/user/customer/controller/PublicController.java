package com.jq.user.customer.controller;

import com.jq.user.customer.service.HealthCheckInnerService;
import com.jq.user.customer.service.SysUserInnerService;
import com.jq.user.customer.service.UserInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brady
 * Descript:
 * Date: 2018/5/25
 */
@RestController
public class PublicController {

    @Resource
    private UserInnerService userInnerService;
    @Resource
    private SysUserInnerService sysUserInnerService;
    @Resource
    private HealthCheckInnerService healthCheckInnerService;

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    /**
     * Author: Brady
     * Description: 用户生成验证码
     * Date: 2018/4/26
     */
    @GetMapping(value = "captcha")
    public void createSecurityCode(HttpServletRequest request) {
        String sessionUuid = request.getSession().getId().replaceAll("\\-", "");
        logger.info("验证码=====》" + sessionUuid);
        userInnerService.createSecurityCode(sessionUuid);
    }

    /**
     * Author: Brady
     * Description: 获取ras公钥
     * Date: 2018/4/26
     */
    @GetMapping(value = "rsa")
    public ApiResult<?> getPublicRsaKey() {
        String rsaPublicKey = userInnerService.getRSAPublicKey();
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("rsaKey", rsaPublicKey);
        return RPCResult.success(resultMap);
    }

    /**
     * check Service 注销不正常service
     * clean Consul unPassing Service
     * PASSING：正常  WARNING  CRITICAL  UNKNOWN：不正常
     * @return
     */
    @GetMapping(value = "checkService")
    public ApiResult<?> checkService() {
        return healthCheckInnerService.healthCheck();
    }


}
