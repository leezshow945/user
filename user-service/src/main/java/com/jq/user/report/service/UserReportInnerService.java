package com.jq.user.report.service;

import java.util.List;
import java.util.Map;

public interface UserReportInnerService extends  UserReportService {

    Map<Long, Integer> getUserUnLoginNumOfDays(List<Long> userIdList);
}
