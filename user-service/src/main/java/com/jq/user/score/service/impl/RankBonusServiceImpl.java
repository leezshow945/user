package com.jq.user.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.framework.core.exception.ParameterValidateException;
import com.jq.platform.code.PlatformCodeEnum;
import com.jq.platform.operating.dto.OperatingLinesInfoDTO;
import com.jq.platform.operating.service.OperatingLinesInfoService;
import com.jq.report.common.constants.ApiConstants;
import com.jq.report.member.dto.MemberDTO;
import com.jq.report.member.service.MemberService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.log.dao.LogUserDao;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserService;
import com.jq.user.score.dao.RankBonusConfigDao;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.entity.RankBonusConfigEntity;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.service.RankBonusInnerService;
import com.jq.user.support.RedisUtil;
import com.liying.common.constants.OrderConstants;
import com.liying.common.constants.UserConstants;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.api.UserFundLogService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.IncomePayReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: Lee
 * @Date: 2018/11/12 14:44
 */
@Service
@Transactional
public class RankBonusServiceImpl extends ServiceImpl<RankBonusConfigDao, RankBonusConfigEntity> implements  RankBonusInnerService {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private OrderSubtotalService orderSubtotalService;
    @Resource
    private UserFundService userFundService;
    @Resource
    private UserFundLogService userFundLogService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private LogUserService logUserService;
    @Resource
    private LogUserDao logUserDao;
    @Resource
    private OperatingLinesInfoService operatingLinesInfoService;
    @Resource
    private MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(RankBonusServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public ApiResult<RankBonusConfigDTO> getRankBonus(Long siteId, String siteCode) {
            if (ObjectUtil.isNull(siteId) && ObjectUtil.isNull(siteCode)) {
                throw new ParameterValidateException("缺少站点信息参数");
            }
            QueryWrapper<RankBonusConfigEntity> ew = new QueryWrapper<>();
            if (ObjectUtil.isNotNull(siteId)) {
                ew.eq("site_id", siteId);
            }
            if (ObjectUtil.isNotNull(siteCode)) {
                ew.eq("site_code", siteCode);
            }
            return RPCResult.success(getOne(ew));
    }


    @Override
    public ApiResult updateRankBonus(RankBonusConfigDTO dto) {
        try {
            Assert.isNull(dto.getSiteId(), "缺少站点信息参数");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date startTime = dateFormat.parse(dto.getBonusStartTime());
            Date endTime = dateFormat.parse(dto.getBonusEndTime());
            //使用Date的compareTo()方法，大于、等于、小于分别返回1、0、-1
            if (endTime.compareTo(startTime) <= 0) {
                return RPCResult.fail(UserCodeEnum.RANKBONUS_TIME_ERROR.getCode(), UserCodeEnum.RANKBONUS_TIME_ERROR.getMessage());
            }
            //判断传入的投注金额值是否正常
            if (dto.getLevel1Bet() >= dto.getLevel2Bet() || dto.getLevel2Bet() >= dto.getLevel3Bet()) {
                return RPCResult.fail(UserCodeEnum.RANKBONUS_LEVELBET_ERROR.getCode(), UserCodeEnum.RANKBONUS_LEVELBET_ERROR.getMessage());
            }
            RankBonusConfigEntity entity = getOne(new QueryWrapper<RankBonusConfigEntity>().eq("site_id", dto.getSiteId()));
            if (entity == null) {
                entity = new RankBonusConfigEntity();
            }
            BeanUtil.copyProperties(dto, entity);
            return saveOrUpdate(entity) ? RPCResult.success() : RPCResult.fail();
        } catch (ParseException e) {
            logger.error("日期格式转换错误：{}",dto.getBonusStartTime());
            throw new UserException(ErrorCode.DEFAULT_CODE,ErrorCode.DEFAULT_MSG);
        }
    }


    @Override
    public ApiResult<UserRankBonusDTO> getUserRankBonus(Long userId) {
            Assert.isNull(userId);
            UserEntity userEntity = userDao.findById(userId);
            if (userEntity == null) {
                throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
            }
            UserRankEntity rankEntity = userRankDao.findById(userEntity.getUserRankId());
            if (rankEntity == null) {
                throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
            }

            //获取昨日用户投注
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setSiteCode(userEntity.getSiteCode());
            memberDTO.setUserId(userEntity.getId());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            memberDTO.setStartTime(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
            memberDTO.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
            memberDTO.setTotalMoneyField(ApiConstants.ConditionField.BET_MONEY);

            ApiResult<Long> betApiResult = memberService.findUserTotalApi(memberDTO);
            logger.info("获取用户加奖参数时查询用户投注,入参" + memberDTO.toString() + ",返回" + betApiResult.toString());
            if (!RPCResult.checkApiResult(betApiResult)) {
                return RPCResult.fail(betApiResult.getCode(), betApiResult.getMessage());
            }
            long dailyBet = 0;//昨日投注金额
            long dailyBonus = 0;//可领取加奖
            if (ObjectUtil.isNotNull(betApiResult.getData())) {
                dailyBet = betApiResult.getData();
            }

            UserRankBonusDTO dto = new UserRankBonusDTO();
            dto.setRankName(rankEntity.getRankName());
            dto.setRankLevel(rankEntity.getRankLevel());

            RankBonusConfigEntity configEntity = getOne(new QueryWrapper<RankBonusConfigEntity>().eq("site_id", userEntity.getSiteId()));
            if (configEntity != null && rankEntity.getBonusType() == 1) {
                //判断接口请求时间是否可领取
                dto.setBonusType(checkBonusTime(new Date(), configEntity.getBonusStartTime(), configEntity.getBonusEndTime()) ? 1 : 0);

                if (dailyBet > 0) {
                    NumberFormat nf = NumberFormat.getPercentInstance();
                    nf.setMinimumFractionDigits(2);

                    //判断投注金额属于哪级的加奖
                    //直接提供百分比字符给前端页面填充
                    if (dailyBet >= configEntity.getLevel3Bet()) {
                        dto.setBonusRatio(nf.format(rankEntity.getLevel3Ratio() * 0.0001));
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel3Ratio());
                    } else if (dailyBet >= configEntity.getLevel2Bet()) {
                        dto.setBonusRatio(nf.format(rankEntity.getLevel2Ratio() * 0.0001));
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel2Ratio());
                    } else if (dailyBet >= configEntity.getLevel1Bet()) {
                        dto.setBonusRatio(nf.format(rankEntity.getLevel1Ratio() * 0.0001));
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel1Ratio());
                    }
                }
            }
            //格式化日加奖与日投注 分化元
            dto.setDailyBonus(new BigDecimal((double) dailyBonus / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            dto.setDailyBet(new BigDecimal((double) dailyBet / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            //根据用户操作日志判断今天是否已领奖
            if (logUserDao.selectCount(new QueryWrapper<LogUserEntity>()
                    .eq("is_del", UserConstant.IS_F)
                    .eq("flag_type", UserCfg.USER_LOG_FLAG_TYPE_OPER)
                    .eq("site_code", userEntity.getSiteCode())
                    .eq("user_id", userEntity.getId())
                    .eq("type", UserCfg.RANK_BONUS)
                    .apply("to_days(create_time)=to_days(now())")) > 0) {

                dto.setBonusType(-1);//设置领奖状态为已领取
            }

            return RPCResult.success(dto);
    }

    //领取每日加奖接口
    @Override
    public ApiResult addRankBonus(LogUserDTO logUserDTO) {
            Assert.isNull(logUserDTO);
            UserEntity userEntity = userDao.findById(logUserDTO.getUserId());
            if (userEntity == null) {
                return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
            UserRankEntity rankEntity = userRankDao.findById(userEntity.getUserRankId());
            if (rankEntity == null) {
                throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
            }

            //计算该用户可领取的每日加奖

            //获取昨日用户投注
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setSiteCode(userEntity.getSiteCode());
            memberDTO.setUserId(userEntity.getId());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            memberDTO.setStartTime(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
            memberDTO.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
            memberDTO.setTotalMoneyField(ApiConstants.ConditionField.BET_MONEY);

            ApiResult<Long> betApiResult = memberService.findUserTotalApi(memberDTO);
            logger.info("获取用户加奖参数时查询用户投注,入参" + memberDTO.toString() + ",返回" + betApiResult.toString());
            if (!RPCResult.checkApiResult(betApiResult)) {
                return RPCResult.fail(betApiResult.getCode(), betApiResult.getMessage());
            }

            long dailyBet = 0;//昨日投注金额
            long dailyBonus = 0;//可领取加奖金额
            if (ObjectUtil.isNotNull(betApiResult.getData())) {
                dailyBet = betApiResult.getData();
            }

            RankBonusConfigEntity configEntity = getOne(new QueryWrapper<RankBonusConfigEntity>().eq("site_id", userEntity.getSiteId()));
            if (configEntity != null && rankEntity.getBonusType() == 1) {
                if (dailyBet > 0) {
                    NumberFormat nf = NumberFormat.getPercentInstance();
                    nf.setMinimumFractionDigits(2);
                    //判断投注金额属于哪级的加奖
                    if (dailyBet >= configEntity.getLevel3Bet()) {
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel3Ratio());
                    } else if (dailyBet >= configEntity.getLevel2Bet()) {
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel2Ratio());
                    } else if (dailyBet >= configEntity.getLevel1Bet()) {
                        dailyBonus = countBonusRatio(dailyBet, rankEntity.getLevel1Ratio());
                    }
                }
            }
            if (dailyBonus > 0) {
                //判断站点运营额度是否欠费
                ApiResult<OperatingLinesInfoDTO> linesInfoDTOApiResult = operatingLinesInfoService.queryLineInfoBySiteCode(userEntity.getSiteCode());
                if (!RPCResult.checkApiResult(linesInfoDTOApiResult)) {
                    logger.error("获取站点运营额度异常：" + linesInfoDTOApiResult.toString());
                    return linesInfoDTOApiResult;
                }
                if (linesInfoDTOApiResult.getData().getCanAmount() < dailyBonus) {
                    //return RPCResult.custom(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), PlatformCodeEnum.QUOTA_NOE_ENOUGH.getMessage());
                    return RPCResult.custom(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), "领取失败，请联系客服人员");
                }

                logger.info(userEntity.getId() + "领取每日加奖，发放金额为" + dailyBonus);
                //使用分布式锁调用出入款接口来发放奖励
                String rankBonusCacheKey = RedisKey.Lock.ADD_RANK_BONUS + userEntity.getId();
                RedisUtil rankBonusRedisUtil = new RedisUtil(redisTemplate, rankBonusCacheKey);
                try {
                    if (rankBonusRedisUtil.lock()) {
                        IncomePayReq req = new IncomePayReq();
                        req.setUserId(userEntity.getId() + "");
                        req.setSiteCode(userEntity.getSiteCode());
                        req.setMoney(dailyBonus + "");
                        req.setTradeType(UserConstants.TradeType.INCOME + "");
                        req.setSubTradeType(UserConstants.TradeType.Income.RANK_BONUS + "");
                        req.setIsDemo(userEntity.getIsDemo() + "");
                        req.setPayNo(OrderConstants.OrderNoPrefix.BATCH + UUID.randomUUID().toString().replace("-", "").toUpperCase());
                        req.setPlatformType(logUserDTO.getPlat() + "");
                        req.setPayType(UserConstant.PayType.PAY_SYSTEM + "");
                        req.setOperTime(new Date());
                        req.setRemark("领取昨日投注加奖");

                        ApiResult result = userFundService.incomePay(req);
                        logger.info("用户领取加奖调用出入款接口,入参" + req.toString() + ",返回" + result.toString());
                        if (!RPCResult.checkApiResult(result)) {
                            return RPCResult.fail(result.getCode(), result.getMessage());
                        }
                        //入款接口调用完毕 生成用户操作日志
                        ApiResult logResult = logUserService.addUserLogApi(logUserDTO);
                        if (!RPCResult.checkApiResult(logResult)) {
                            return RPCResult.fail(logResult.getCode(), logResult.getMessage());
                        }
                    }
                } catch (Exception e) {
                    logger.error("领取加奖出现异常:", e);
                    throw new UserException(UserCodeEnum.ADD_RANKBONUS_ERROR.getCode(), UserCodeEnum.ADD_RANKBONUS_ERROR.getMessage());
                } finally {
                    rankBonusRedisUtil.unlock();
                }
                return RPCResult.success();
            } else {
                return RPCResult.fail(UserCodeEnum.RANKBONUS_IS_ZERO.getCode(), UserCodeEnum.RANKBONUS_IS_ZERO.getMessage());
            }

    }

    //检查请求时间是否在开奖时间范围内
    public boolean checkBonusTime(Date sourceDate, String startTime, String endTime) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String nowTime = dateFormat.format(sourceDate);

            Calendar now = Calendar.getInstance();
            now.setTime(dateFormat.parse(nowTime));

            Calendar begin = Calendar.getInstance();
            begin.setTime(dateFormat.parse(startTime));

            Calendar end = Calendar.getInstance();
            end.setTime(dateFormat.parse(endTime));

            //逾期时间为0点则表示全天不逾期 只需判断开始时间
            if (endTime.equals("00:00")) {
                return (now.after(begin) || nowTime.equals(startTime));
            } else {
                if (nowTime.equals(startTime) || nowTime.equals(endTime)) {
                    return true;
                }
                if (now.after(begin) && now.before(end)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("",e);
            return false;
        }

    }

    //将用户资金乘以倍率后返回分为单位的数据
    public long countBonusRatio(long money, int ratio) {
        double r = new BigDecimal((double) ratio / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return new BigDecimal((double) money * r / 100).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
    }


    public static void main(String[] args) {
        try {

//            double r = new BigDecimal((double)12000/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("倍率"+r+"%");
//
//            double d1 = new BigDecimal((double)0*r/100/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("0分钱乘以的结果为"+d1);
//
//            double d2 = new BigDecimal((double)1*r/100/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("1分钱乘以的结果为"+d2);
//
//            double d3 = new BigDecimal((double)10*r/100/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("1毛钱乘以的结果为"+d3);
//
//            double d4 = new BigDecimal((double)200*r/100/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("2块钱乘以的结果为"+d4);
//
//            double d5 = new BigDecimal((double)3000*r/100/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            System.out.println("30块钱乘以的结果为"+d5);
//
//            long vaule = new BigDecimal((double)1000*r/100).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
//            System.out.println("10块钱乘以的结果为"+vaule+"分");


            double ratio = 0.99;
            System.out.println("后台设定奖励倍数为" + ratio + "倍");
            System.out.println("用户充值1元奖励" + new BigDecimal((double) 1 * ratio).setScale(0, BigDecimal.ROUND_HALF_UP).toString() + "分");
            System.out.println("用户充值10元奖励" + new BigDecimal((double) 10 * ratio).setScale(0, BigDecimal.ROUND_HALF_UP).toString() + "分");
            System.out.println("用户充值100元奖励" + new BigDecimal((double) 100 * ratio).setScale(0, BigDecimal.ROUND_HALF_UP).toString() + "分");
//            String money = "5000";
//            double ratio = 3.88;
//            System.out.println(new BigDecimal(Double.parseDouble(money) * ratio / 100).setScale(2, BigDecimal.ROUND_HALF_UP).longValue());


//            NumberFormat nf=NumberFormat.getPercentInstance();
//            nf.setMinimumFractionDigits(2);
//
//            System.out.println(nf.format(0));
//            System.out.println(nf.format(1*0.0001));
//            System.out.println(nf.format(100*0.0001));
//            System.out.println(nf.format(300*0.0001));

//            String d = "897.98";
//            BigDecimal b = new BigDecimal(d);
//            long l =b.multiply(new BigDecimal((double) 100)).longValue();
//            System.out.println(String.valueOf(l));

//            String startTime="15:48";
//            String endTime="00:00";
//            Date sourceDate = new Date();
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//            String nowTime =dateFormat.format(sourceDate);
//
//            Calendar now = Calendar.getInstance();
//            now.setTime(dateFormat.parse(nowTime));
//
//            Calendar begin = Calendar.getInstance();
//            begin.setTime(dateFormat.parse(startTime));
//
//            Calendar end = Calendar.getInstance();
//            end.setTime(dateFormat.parse(endTime));
//
//            //逾期时间为0点则表示全天不逾期 只需判断开始时间
//            if(endTime.equals("00:00")){
//                System.out.println((now.after(begin)||nowTime.equals(startTime)));
//            }else {
//                if (nowTime.equals(startTime) || nowTime.equals(endTime)) {
//                    System.out.println(true);
//                }
//                if (now.after(begin) && now.before(end)) {
//                    System.out.println(true);
//                } else {
//                    System.out.println(false);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
