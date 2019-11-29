package com.jq.user.rebate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.user.rebate.dao.RebateResultInfoDao;
import com.jq.user.rebate.entity.RebateResultInfoEntity;
import com.jq.user.rebate.service.RebateResultInfoInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RebateResultInfoServiceImpl extends ServiceImpl<RebateResultInfoDao, RebateResultInfoEntity> implements RebateResultInfoInnerService {

}


