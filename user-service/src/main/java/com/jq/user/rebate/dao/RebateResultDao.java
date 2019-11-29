package com.jq.user.rebate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.jq.user.rebate.entity.RebateResultEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RebateResultDao extends BaseMapper<RebateResultEntity> {

    /**
     * 验证是否存在相同起止时间的返水记录
     * @param dto
     * @return
     */
    RebateResultDTO getResultByTime(RebateResultDTO dto);

    /**
     * 获取返水结果记录
     * @param dto
     * @return
     */
    List<RebateResultDTO> queryList(@Param("dto") RebateResultDTO dto, Page page);

    /**
     * 统计时间内id集合的返水金额
     * siteCode  beginTime  endTime  reqIds
     * @return
     */
    RebateResultInfoDTO sumRebateResultsByIds(RebateResultDTO dto);


    /**
     * 根据条件统计时间内用户个人返水金额
     * siteId  beginTime  endTime  reqIds
     * @return
     */
    List<Map<String,Long>> getUserAllRebateMap(RebateResultDTO dto);
}
