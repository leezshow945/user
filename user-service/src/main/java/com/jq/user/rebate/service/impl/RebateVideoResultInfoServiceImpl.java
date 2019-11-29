package com.jq.user.rebate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.user.rebate.dao.RebateVideoResultInfoDao;
import com.jq.user.rebate.entity.RebateVideoResultInfoEntity;
import com.jq.user.rebate.service.RebateVideoResultInfoInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Lee
 * @Date: 2018/12/5 14:00
 */
@Transactional
@Service
public class RebateVideoResultInfoServiceImpl extends ServiceImpl<RebateVideoResultInfoDao, RebateVideoResultInfoEntity> implements RebateVideoResultInfoInnerService {

}

