package com.jq.user.customer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.Check;
import com.jq.user.customer.service.HealthCheckInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class HealthCheckServiceImpl implements HealthCheckInnerService {

    @Resource
    private ConsulClient consulClient;

    @Override
    public ApiResult healthCheck() {
        Iterator<Map.Entry<String, Check>> it = consulClient.getAgentChecks().getValue().entrySet().iterator();
        Map.Entry<String, Check> serviceMap = null;
        List<JSONObject> serviceList = new ArrayList<>();
        while (it.hasNext()) {
            serviceMap = it.next();
            JSONObject serviceJson = new JSONObject();
            String serviceId =serviceMap.getValue().getServiceId();
            serviceJson.put("ServiceId",serviceId);
            serviceJson.put("ServiceName",serviceMap.getValue().getServiceName());
            serviceJson.put("ServiceStatus",serviceMap.getValue().getStatus());
            serviceList.add(serviceJson);
            if (ObjectUtil.notEqual(serviceMap.getValue().getStatus(), Check.CheckStatus.PASSING)) {
                consulClient.agentServiceDeregister(serviceId);
            }
        }
        return RPCResult.success(serviceList);
    }
}
