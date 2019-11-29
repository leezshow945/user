package com.jq.user.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.code.PlatformCodeEnum;
import com.jq.platform.operating.dto.OperatingLinesInfoDTO;
import com.jq.platform.operating.service.OperatingLinesInfoService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.ScoreTypeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.service.LogUserService;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.score.dao.RankBonusConfigDao;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dao.UserRankScoreDao;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.dto.UserRankScoreDTO;
import com.jq.user.score.entity.RankBonusConfigEntity;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserRankScoreEntity;
import com.jq.user.score.service.UserRankInnerService;
import com.jq.user.score.service.UserRankScoreInnerService;
import com.jq.user.score.service.UserScoreInnerService;
import com.jq.user.support.PageUtil;
import com.jq.user.support.RedisUtil;
import com.jq.user.support.SupportUtil;
import com.liying.common.constants.OrderConstants;
import com.liying.common.constants.UserConstants;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.IncomePayReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service
@Transactional
public class UserRankServiceImpl extends ServiceImpl<UserRankDao, UserRankEntity> implements UserRankInnerService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserRankScoreDao userRankScoreDao;
    @Resource
    private RankBonusConfigDao rankBonusConfigDao;
    @Resource
    private UserRankInnerService userRankInnerService;
    @Resource
    private UserScoreInnerService userScoreInnerService;
    @Resource
    private UserRankScoreInnerService userRankScoreInnerService;
    @Resource
    private KeyValueService keyValueService;
    @Resource
    private UserFundService userFundService;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private LogUserService logUserService;
    @Resource
    private OperatingLinesInfoService operatingLinesInfoService;

    private static final Logger logger = LoggerFactory.getLogger(UserRankServiceImpl.class);


    /**
     * 查询等级模板信息
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserRankEntity> findAll(Long siteId, UserRankEntity userRankEntity, Page page) {

        QueryWrapper<UserRankEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        if (siteId != null && siteId != 0L) {//判断站点与平台用户
            ew.eq("site_id", siteId);
        }
        if (userRankEntity.getRankLevel() != null) {
            ew.eq("rank_level", userRankEntity.getRankLevel());
        }
        if (StrUtil.isNotEmpty(userRankEntity.getRankName())) {
            ew.like("rank_name", userRankEntity.getRankName());
        }
        if (StrUtil.isNotEmpty(userRankEntity.getTimeStr())) {
            ew.eq("subString(create_time,1,10)", userRankEntity.getTimeStr());
        }
        if (userRankEntity.getMaxScore() != null) {
            ew.eq("max_score", userRankEntity.getMaxScore());
        }
        return userRankDao.selectPage(page, ew).getRecords();
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isMaxScore(Long siteId, Integer maxScore) {
        return userRankDao.selectCount(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)
                .ge("max_score", maxScore)) == 0;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean checkMaxScoreUp(Long siteId, Integer maxScore, Integer rankLevel) {
        return userRankDao.selectCount(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)
                .gt("rank_level", rankLevel)
                .le("max_score", maxScore)) == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkMaxScoreDown(Long siteId, Integer maxScore, Integer rankLevel) {
        return userRankDao.selectCount(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)
                .lt("rank_level", rankLevel)
                .ge("max_score", maxScore)) == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public UserRankEntity findById(Long rankId) {
        return userRankDao.findById(rankId);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existRankName(Long siteId, String rankName) {
        return userRankDao.selectCount(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)
                .eq("rank_name", rankName)) == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMaxRankLevel(Long siteId, Integer rankLevel) {
        return userRankDao.selectCount(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)
        ).equals(rankLevel);
    }


    @Override
    public boolean add(UserRankEntity userRankEntity, List<UserRankScoreEntity> scoreEntityList) {
        return userRankDao.insert(userRankEntity) > 0 && userRankScoreDao.addList(scoreEntityList) > 0;
    }

    @Override
    public boolean update(UserRankEntity userRankEntity, List<UserRankScoreEntity> newEntityList, List<UserRankScoreEntity> oldEntityList) {
        if (newEntityList.size() > 0) {
            return userRankDao.updateById(userRankEntity) > 0 && userRankScoreDao.updateList(oldEntityList) > 0 && userRankScoreDao.addList(newEntityList) > 0;
        } else {
            return userRankDao.updateById(userRankEntity) > 0 && userRankScoreDao.updateList(oldEntityList) > 0;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List getRankLevelList(Long siteId) {
        return userRankDao.selectRankLevel(siteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Integer> findAllRankMapBySiteId(Long siteId) {
        QueryWrapper<UserRankEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("site_id", siteId);
        ew.orderByAsc("rank_level");
        List<UserRankEntity> resultList = userRankDao.selectList(ew);
        Map<Long, Integer> resultMap = new HashMap<>();
        for (UserRankEntity userRankEntity : resultList) {
            resultMap.put(userRankEntity.getId(), userRankEntity.getRankLevel());
        }
        return resultMap;
    }


    /**
     * 检查用户接口方法
     * 1.更新用户积分
     * 2.检查用户所达到的最新等级
     * 3.根据晋级奖励与跳级加奖发放奖励到user信息内
     *
     * @param userEntity
     * @return
     */

    @Override
    public boolean checkUserScore(UserEntity userEntity) {

        //查询用户当前积分所处最大等级(最大积分减去用户当前积分得到积分最接近的数据)
        QueryWrapper<UserRankEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("site_id", userEntity.getSiteId());
        ew.le("max_score", userEntity.getScore());
        ew.orderByAsc("ABS(max_score-" + userEntity.getScore() + ")");
        ew.last("limit 1");
        UserRankEntity newRank = getOne(ew);

        if (newRank != null && !userEntity.getUserRankId().equals(newRank.getId())) {
            UserRankEntity oldRank = getById(userEntity.getUserRankId());
            userEntity.setUserRankId(newRank.getId());
            if (oldRank != null) {
                long totalReward = 0l;
                StringBuffer msg = new StringBuffer();
                msg.append(userEntity.getId())
                        .append("用户升级,当前")
                        .append(oldRank.getRankLevel())
                        .append(",升级为")
                        .append(newRank.getRankLevel());
                //获取此次升级的所有等级，进行遍历计算晋级奖励
                QueryWrapper<UserRankEntity> upEw = new QueryWrapper<>();
                upEw.eq("is_del", UserConstant.IS_F);
                upEw.eq("site_id", userEntity.getSiteId());
                upEw.gt("max_score", oldRank.getMaxScore());
                upEw.le("max_score", newRank.getMaxScore());
                upEw.gt("upgrade_reward", 0);
                List<UserRankEntity> upList = list(upEw);
                for (UserRankEntity thisRank : upList) {
                    msg.append(";升到")
                            .append(thisRank.getRankLevel())
                            .append("级奖励(分)")
                            .append(thisRank.getUpgradeReward());
                    totalReward += thisRank.getUpgradeReward();
                }
                if (totalReward > 0) {
                    msg.append((";此次升级总奖励金额(分)为")).append(totalReward);
                    //如果一次升多级 则为跳级 进行跳级加奖判断
                    if (upList.size() > 1) {
                        RankBonusConfigEntity configEntity = rankBonusConfigDao.selectById(userEntity.getSiteId());
                        if (configEntity != null && configEntity.getUpgradeRatio() > 0) {
                            double r = new BigDecimal((double) configEntity.getUpgradeRatio() / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            //计算晋级跳级奖励时 先这算成元为单位的无条件进位数值 再重新*100以分为单位进行数据传输
                            totalReward = new BigDecimal((double) totalReward * r / 100 / 100).setScale(0, BigDecimal.ROUND_UP).longValue() * 100;
                            msg.append(";折换跳奖比例为")
                                    .append(r)
                                    .append("%,计算出跳级奖励为(分)")
                                    .append(totalReward);
                        }
                    }
                    msg.append(";当前未领取晋级奖励(分)为")
                            .append(userEntity.getUpgradeBonus())
                            .append(",此次晋级增加晋级奖励(分)")
                            .append(totalReward);
                    userEntity.setUpgradeBonus(userEntity.getUpgradeBonus() + totalReward);
                    logger.info(msg.toString());
                }
            }
        }
        return userDao.updateById(userEntity) > 0;
    }

    @Override
    public UserRankDTO getRankInfoById(Long rankId) {
        UserRankEntity userRankEntity = userRankInnerService.findById(rankId);
        if (userRankEntity == null) {
            throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
        }
        UserRankDTO userRankDTO = new UserRankDTO();
        BeanUtil.copyProperties(userRankEntity, userRankDTO);
        //获取站点等级加奖信息
        RankBonusConfigEntity configEntity = rankBonusConfigDao.selectById(userRankEntity.getSiteId());
        if (ObjectUtil.isNotNull(configEntity)) {
            RankBonusConfigDTO configDTO = new RankBonusConfigDTO();
            BeanUtil.copyProperties(configEntity, configDTO);
            userRankDTO.setRankBonusConfigDTO(configDTO);
        }
        List<UserRankScoreEntity> userRankScoreEntityList = userRankScoreInnerService.findByRankId(userRankEntity.getId());
        List<UserRankScoreEntity> addList = new ArrayList<>();//补全数据
        Date nowDate = new Date();
        //根据字典表初始化会员积分获取详情数据
        com.liying.common.service.ApiResult<Map<String, String>> keyValues = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
        if (RPCResult.checkApiResult(keyValues)) {
            loop:
            for (Map.Entry<String, String> entry : keyValues.getData().entrySet()) {
                for (UserRankScoreEntity us : userRankScoreEntityList) {
                    if (us.getScoreCode().equals(entry.getKey())) {
                        continue loop;
                    }
                }
                UserRankScoreEntity entity = new UserRankScoreEntity();
                entity.setId(IdWorker.getId());
                entity.setRankId(rankId);
                entity.setScoreName(entry.getValue());
                entity.setScoreCode(entry.getKey());
                entity.setScoreVal(0);
                entity.setIsEnable(UserConstant.IS_F);
                entity.setCreateTime(nowDate);
                entity.setUpdateTime(nowDate);
                entity.setCreateBy(userRankEntity.getCreateBy());
                entity.setUpdateBy(userRankEntity.getCreateBy());
                entity.setIsDel(UserConstant.IS_F);
                addList.add(entity);
                userRankScoreEntityList.add(entity);
            }
        }
        List<UserRankScoreDTO> userRankScoreDTOList = new ArrayList<>();
        if (addList.size() > 0) {
            if (userRankScoreDao.addList(addList) <= 0) {
                return null;
            }
        }
        for (UserRankScoreEntity URankScoreEntity : userRankScoreEntityList) {
            UserRankScoreDTO userRankScoreDTO = new UserRankScoreDTO();
            BeanUtil.copyProperties(URankScoreEntity, userRankScoreDTO);
            userRankScoreDTOList.add(userRankScoreDTO);
        }
        userRankDTO.setUserRankScoreList(userRankScoreDTOList);
        return userRankDTO;
    }

    @Override
    public List<UserRankDTO> queryRankListBySiteId(Long siteId) {
        List<UserRankDTO> userRankDTOList = new ArrayList<>();
        List<UserRankEntity> userRankEntityList = list(new QueryWrapper<UserRankEntity>()
                .eq("is_del", UserConstant.IS_F)
                .eq("site_id", siteId)
                .orderByAsc("rank_level"));
        RankBonusConfigEntity configEntity = rankBonusConfigDao.selectById(siteId);
        Integer upgradeRatio = 0;
        if (configEntity != null) {
            upgradeRatio = configEntity.getUpgradeRatio();
        }
        Long upgradeReward = 0l;//初始化总晋级奖励金(分)
        String upgradeRankBonus = "0";//初始化该等级跳级奖励金
        for (UserRankEntity entity : userRankEntityList) {

            UserRankDTO dto = new UserRankDTO();
            BeanUtil.copyProperties(entity, dto);
            dto.setRankImg(SupportUtil.getRankImgByLevel(dto.getRankLevel()));

            //判断跳级奖励是否配置
            if (upgradeRatio > 0) {
                upgradeReward += entity.getUpgradeReward();
                //从2级开始才符合跳级奖励规则
                if (entity.getRankLevel() > 1) {
                    double r = new BigDecimal((double) configEntity.getUpgradeRatio() / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //跳级奖励金乘以系数 r% 除以100 再除以100得到跳级奖励元
                    upgradeRankBonus = new BigDecimal((double) upgradeReward * r / 100 / 100).setScale(0, BigDecimal.ROUND_UP).toString();
                }
            }
            dto.setUpgradeRankBonus(upgradeRankBonus);
            //格式化晋级奖励金字段
            dto.setUpgradeReward(dto.getUpgradeReward() / 100);
            List<UserRankScoreEntity> UserRankScoreEntityList = userRankScoreInnerService.findByRankId(entity.getId());
            List<UserRankScoreDTO> userRankScoreDTOList = new ArrayList<>();

            for (UserRankScoreEntity URankScoreEntity : UserRankScoreEntityList) {
                UserRankScoreDTO userRankScoreDTO = new UserRankScoreDTO();
                BeanUtil.copyProperties(URankScoreEntity, userRankScoreDTO);
                userRankScoreDTOList.add(userRankScoreDTO);
            }
            dto.setUserRankScoreList(userRankScoreDTOList);
            userRankDTOList.add(dto);
        }
        return userRankDTOList;
    }


    @Override
    @Transactional(readOnly = true)
    public ApiResult<Long> getDefaultIdBySiteId(Long siteId) {
        Long rankId = userRankDao.findDefaultRank(siteId);
        if (rankId != null) {
            return RPCResult.success(rankId);
        } else {
            return RPCResult.custom(UserCodeEnum.RANK_MISS_CODE.getCode(), UserCodeEnum.RANK_MISS_CODE.getMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public ApiResult<PageInfo<UserRankDTO>> queryRankListAPI(UserRankDTO userRankDTO) {
        UserRankEntity userRankEntity = new UserRankEntity();
        BeanUtil.copyProperties(userRankDTO, userRankEntity);

        Page page = PageUtil.buildPage(userRankDTO.getPage(), userRankDTO.getLimit(), userRankDTO.getOrderDirection(), userRankDTO.getOrderField());

        List<UserRankEntity> userRankEntityList = this.findAll(userRankDTO.getSiteId(), userRankEntity, page);
        List<UserRankDTO> userRankDTOList = new ArrayList<>();

        for (UserRankEntity UREntity : userRankEntityList) {
            UserRankDTO newUserRankDTO = new UserRankDTO();
            BeanUtil.copyProperties(UREntity, newUserRankDTO);
            newUserRankDTO.setRankImg(SupportUtil.getRankImgByLevel(UREntity.getRankLevel()));

            List<UserRankScoreEntity> UserRankScoreEntityList = userRankScoreInnerService.findByRankId(UREntity.getId());
            List<UserRankScoreDTO> userRankScoreDTOList = new ArrayList<>();

            for (UserRankScoreEntity URankScoreEntity : UserRankScoreEntityList) {
                UserRankScoreDTO userRankScoreDTO = new UserRankScoreDTO();
                BeanUtil.copyProperties(URankScoreEntity, userRankScoreDTO);
                userRankScoreDTOList.add(userRankScoreDTO);
            }
            newUserRankDTO.setUserRankScoreList(userRankScoreDTOList);
            userRankDTOList.add(newUserRankDTO);
        }
        PageInfo<UserRankDTO> listPageInfo = new PageInfo<>(userRankDTOList, userRankDTO.getPage(), userRankDTO.getLimit(), page.getTotal());
        return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<List<UserRankDTO>> queryRankBySiteIdAPI(Long siteId) {
        return RPCResult.success(queryRankListBySiteId(siteId));
    }

    /**
     * 初始化B端 vip奖励参数设置
     *
     * @param siteId
     * @return
     */

    @Override
    public ApiResult<UserRankDTO> initRankInfoAPI(Long siteId) {
        Assert.isNull(siteId, "缺少站点信息");
        UserRankDTO userRankDTO = new UserRankDTO();
        //获取站点等级加奖信息
        RankBonusConfigEntity configEntity = rankBonusConfigDao.selectById(siteId);
        if (ObjectUtil.isNotNull(configEntity)) {
            RankBonusConfigDTO configDTO = new RankBonusConfigDTO();
            BeanUtil.copyProperties(configEntity, configDTO);
            userRankDTO.setRankBonusConfigDTO(configDTO);
        }
        //获取最大站点等级
        userRankDTO.setRankLevel(count(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)));

        //根据字典表初始化会员积分获取详情数据
        List<UserRankScoreDTO> scoreDTOList = new ArrayList<>();
        ApiResult<Map<String, String>> keyValues = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
        if (RPCResult.checkApiResult(keyValues)) {
            for (Map.Entry<String, String> entry : keyValues.getData().entrySet()) {
                UserRankScoreDTO scoreDTO = new UserRankScoreDTO();
                scoreDTO.setScoreCode(entry.getKey());
                scoreDTO.setScoreName(entry.getValue());
                scoreDTO.setScoreVal(0);
                scoreDTO.setIsEnable(UserConstant.IS_F);
                scoreDTOList.add(scoreDTO);
            }
            userRankDTO.setUserRankScoreList(scoreDTOList);
            return RPCResult.success(userRankDTO);
        } else {
            return RPCResult.fail(keyValues.getCode(), keyValues.getMessage());
        }
    }


    @Override
    public ApiResult<UserRankDTO> getRankInfoByIdAPI(Long rankId) {
        UserRankDTO userRankDTO = this.getRankInfoById(rankId);
        return RPCResult.success(userRankDTO);
    }

    @Override
    public ApiResult<Integer> getMaxRankLevelAPI(Long siteId) {
        return RPCResult.success(count(new QueryWrapper<UserRankEntity>()
                .eq("site_id", siteId)
                .eq("is_del", UserConstant.IS_F)));
    }


    @Override
    public ApiResult addRankInfoAPI(UserRankDTO userRankDTO) {
        Assert.isNull(userRankDTO.getRankName(), "等级称号不能为空");
        Assert.isNull(userRankDTO.getRankLevel(), "等级不能为空");
        Assert.isNull(userRankDTO.getMaxScore(), "最大积分不能为空");

        //判断提交参数是否合理
        if (!userRankInnerService.existRankName(userRankDTO.getSiteId(), userRankDTO.getRankName())) {
            throw new UserException(UserCodeEnum.RANK_NAME_CODE.getCode());
        }
        if (!userRankInnerService.isMaxRankLevel(userRankDTO.getSiteId(), userRankDTO.getRankLevel())) {
            throw new UserException(UserCodeEnum.RANK_LEVEL_CODE.getCode());
        }
        if (!userRankInnerService.isMaxScore(userRankDTO.getSiteId(), userRankDTO.getMaxScore())) {
            throw new UserException(UserCodeEnum.MAXSCORE_ISNOT_MAX.getCode());
        }

        Date nowDate = new Date();
        //等级模板入参
        UserRankEntity userRankEntity = new UserRankEntity();
        BeanUtil.copyProperties(userRankDTO, userRankEntity);
        userRankEntity.setId(IdWorker.getId());//生成等级模板id
        userRankEntity.setIsDel(UserConstant.IS_F);
        userRankEntity.setUpdateBy(userRankDTO.getCreateBy());
        userRankEntity.setCreateTime(nowDate);
        userRankEntity.setUpdateTime(nowDate);

        //积分模板入参
        List<UserRankScoreEntity> scoreEntityList = new ArrayList<>();
        //获取积分字典值
        com.liying.common.service.ApiResult<Map<String, String>> keyValues = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
        if (RPCResult.checkApiResult(keyValues)) {
            for (Map.Entry<String, String> entry : keyValues.getData().entrySet()) {
                UserRankScoreEntity entity = new UserRankScoreEntity();
                entity.setId(IdWorker.getId());
                Object enableVal = userRankDTO.getRankMap().get(entry.getKey() + "_check");//是否启用标识
                Object val = userRankDTO.getRankMap().get(entry.getKey() + "_val");

                entity.setRankId(userRankEntity.getId());
                entity.setScoreName(entry.getValue());
                entity.setScoreCode(entry.getKey());
                entity.setScoreVal(StrUtil.isBlankIfStr(val) ? 0 : Integer.valueOf(val.toString()));
                entity.setIsEnable(StrUtil.isBlankIfStr(enableVal) ? UserConstant.IS_F : Integer.valueOf(enableVal.toString()));

                entity.setCreateTime(nowDate);
                entity.setUpdateTime(nowDate);
                entity.setCreateBy(userRankEntity.getCreateBy());
                entity.setUpdateBy(userRankEntity.getCreateBy());
                entity.setIsDel(UserConstant.IS_F);
                scoreEntityList.add(entity);
            }
            return (userRankInnerService.add(userRankEntity, scoreEntityList)) ? RPCResult.success() : RPCResult.fail();
        } else {
            return new ApiResult(keyValues.getCode(), keyValues.getMessage());
        }
    }

    @Override
    public ApiResult updateRankInfoAPI(UserRankDTO userRankDTO) {
        Assert.isNull(userRankDTO.getRankLevel(), "等级不能为空");
        Assert.isNull(userRankDTO.getMaxScore(), "最大积分不能为空");

        UserRankEntity userRankEntity = userRankInnerService.findById(userRankDTO.getId());
        if (userRankEntity == null) {
            throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
        }

        //表单入参验证
        if (!userRankEntity.getRankName().equals(userRankDTO.getRankName())) {
            if (!userRankInnerService.existRankName(userRankDTO.getSiteId(), userRankDTO.getRankName())) {
                throw new UserException(UserCodeEnum.RANK_NAME_CODE.getCode());
            } else {
                userRankEntity.setRankName(userRankDTO.getRankName());
            }
        }
        if (!userRankDTO.getRankLevel().equals(userRankEntity.getRankLevel())) {
            throw new UserException(UserCodeEnum.RANK_LEVEL_CODE.getCode());
        }
        if (!userRankEntity.getMaxScore().equals(userRankDTO.getMaxScore())) {
            if (!userRankInnerService.checkMaxScoreDown(userRankDTO.getSiteId(), userRankDTO.getMaxScore(), userRankDTO.getRankLevel())) {
                throw new UserException(UserCodeEnum.MAXSCORE_TOO_SMALL.getCode());
            }
            if (!userRankInnerService.checkMaxScoreUp(userRankDTO.getSiteId(), userRankDTO.getMaxScore(), userRankDTO.getRankLevel())) {
                throw new UserException(UserCodeEnum.MAXSCORE_TOO_LARGE.getCode());
            }
            userRankEntity.setMaxScore(userRankDTO.getMaxScore());
        }

        Date nowDate = new Date();
        //将未修改字段赋值到dto 然后整体复制修改
        userRankDTO.setCreateTime(userRankEntity.getCreateTime());
        userRankDTO.setSiteCode(userRankEntity.getSiteCode());
        userRankDTO.setCreateBy(userRankEntity.getCreateBy());

        BeanUtil.copyProperties(userRankDTO, userRankEntity);
        userRankEntity.setUpdateTime(nowDate);

        //遍历原有的积分获取模板并根据最新字典表值进行新增修改处理
        List<UserRankScoreEntity> scoreEntityList = userRankScoreInnerService.findByRankId(userRankEntity.getId());
        List<UserRankScoreEntity> newEntityList = new ArrayList<>();
        com.liying.common.service.ApiResult<Map<String, String>> keyValues = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
        if (RPCResult.checkApiResult(keyValues)) {
            loop:
            for (Map.Entry<String, String> entry : keyValues.getData().entrySet()) {
                Object enableVal = userRankDTO.getRankMap().get(entry.getKey() + "_check");
                Object val = userRankDTO.getRankMap().get(entry.getKey() + "_val");
                for (UserRankScoreEntity us : scoreEntityList) {
                    if (us.getScoreCode().equals(entry.getKey())) {
                        us.setUpdateBy(userRankDTO.getCreateBy());
                        us.setUpdateTime(nowDate);
                        us.setScoreName(entry.getValue());
                        us.setScoreVal(StrUtil.isBlankIfStr(val) ? 0 : Integer.valueOf(val.toString()));
                        us.setIsEnable(StrUtil.isBlankIfStr(enableVal) ? UserConstant.IS_F : Integer.valueOf(enableVal.toString()));
                        continue loop;
                    }
                }

                UserRankScoreEntity entity = new UserRankScoreEntity();
                entity.setId(IdWorker.getId());
                entity.setRankId(userRankEntity.getId());
                entity.setScoreName(entry.getValue());
                entity.setScoreCode(entry.getKey());
                entity.setScoreVal(StrUtil.isBlankIfStr(val) ? 0 : Integer.valueOf(val.toString()));
                entity.setIsEnable(StrUtil.isBlankIfStr(enableVal) ? UserConstant.IS_F : Integer.valueOf(enableVal.toString()));

                entity.setCreateTime(nowDate);
                entity.setUpdateTime(nowDate);
                entity.setCreateBy(userRankEntity.getCreateBy());
                entity.setUpdateBy(userRankEntity.getCreateBy());
                entity.setIsDel(UserConstant.IS_F);
                newEntityList.add(entity);
            }
            return (userRankInnerService.update(userRankEntity, newEntityList, scoreEntityList)) ? RPCResult.success() : RPCResult.fail();
        } else {
            return RPCResult.custom(keyValues.getCode(), keyValues.getMessage());
        }
    }

    @Override
    public ApiResult<List<String>> getAllRankBySiteIdApi(Long siteId) {
        QueryWrapper<UserRankEntity> rankEw = new QueryWrapper<>();
        rankEw.select("distinct rank_level");
        if (siteId != null) {
            rankEw.eq("site_id", siteId);
        }
        List<Map<String, Object>> rank = userRankDao.selectMaps(rankEw);
        List<String> rankList = new ArrayList<>();
        for (Map levelMap : rank) {
            rankList.add(String.valueOf(levelMap.get("rank_level")));
        }

        return RPCResult.success(rankList);
    }

    @Override
    public ApiResult<List<UserRankScoreDTO>> getRankScoreInfo(Long siteId, Long userId) {
        UserEntity userEntity = userDao.findById(userId);
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserRankEntity userRankEntity = userRankInnerService.findById(userEntity.getUserRankId());
        if (userRankEntity == null) {
            throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
        }
        List<UserRankScoreEntity> scoreEntityList = userRankScoreInnerService.findByRankId(userRankEntity.getId());
        com.liying.common.service.ApiResult<Map<String, String>> keyValues = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE_URL);
        if (!RPCResult.checkApiResult(keyValues)) {
            return RPCResult.custom(keyValues.getCode(), keyValues.getMessage());
        }
        List<UserRankScoreDTO> userRankScoreDTOList = new ArrayList<>();
        for (UserRankScoreEntity entity : scoreEntityList) {
            if (entity.getIsEnable().equals(UserConstant.IS_F)) {
                continue;
            }
            UserRankScoreDTO dto = new UserRankScoreDTO();
            BeanUtil.copyProperties(entity, dto);
            String scoreCode = dto.getScoreCode();
            dto.setIsDone(userScoreInnerService.isExistRecord(siteId, userId, dto.getScoreCode()));
            dto.setScoreTypeUrl(ObjectUtil.isNull(keyValues.getData().get(scoreCode)) ? "" : keyValues.getData().get(scoreCode));
//                if (scoreCode.equals(ScoreTypeEnum.SCORE_TYPE_COMPLETE_USER.getCode())) {
//                    //一次性任务
//                    dto.setScoreType(UserConstant.ScoreType.ONETIME);
//                    userRankScoreDTOList.add(dto);
//                } else
            if (scoreCode.equals(ScoreTypeEnum.SCORE_TYPE_ORDER.getCode()) ||
                    scoreCode.equals(ScoreTypeEnum.SCORE_TYPE_SIGN.getCode()) ||
                    scoreCode.equals(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode())) {
                //今日任务
                dto.setScoreType(UserConstant.ScoreType.ONTODAY);
                userRankScoreDTOList.add(dto);
            } else if (scoreCode.equals(ScoreTypeEnum.SCORE_TYPE_AGENT_RECHARGE.getCode())) {
                //无限任务
                dto.setScoreType(UserConstant.ScoreType.UNLIMITED);
                userRankScoreDTOList.add(dto);
            }
        }
        return RPCResult.success(userRankScoreDTOList);
    }

    @Override
    public ApiResult<List<UserRankDTO>> getRankAndRebateLevel(List<UserRankDTO> list) {
        List<UserRankDTO> userRankDTOList = new ArrayList<>();
        if (list != null && list.size() > 0 && StrUtil.isNotEmpty(list.get(0).getSiteCode())) {
            userRankDTOList = userRankDao.getRankAndRebateLevel(list.get(0).getSiteCode(), list);
        }
        for (UserRankDTO userRankDTO : userRankDTOList) {
            String path = userProxyInnerService.getPath(userRankDTO.getUserId(), userRankDTO.getSiteId());
            userRankDTO.setPath(path);
        }
        return RPCResult.success(userRankDTOList);
    }

    @Override
    public ApiResult<UserRankBonusDTO> getRankUpgradeBonus(Long userId) {
        Assert.isNull(userId);
        UserEntity userEntity = userDao.findById(userId);
        if (userEntity == null) {
            return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserRankEntity rankEntity = userRankDao.findById(userEntity.getUserRankId());
        if (rankEntity == null) {
            throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode());
        }
        UserRankBonusDTO dto = new UserRankBonusDTO();
        dto.setDailyBonus(new BigDecimal((double) userEntity.getUpgradeBonus() / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        dto.setRankName(rankEntity.getRankName());
        dto.setRankLevel(rankEntity.getRankLevel());

        return RPCResult.success(dto);
    }

    @Override
    public ApiResult addRankUpgradeBonus(LogUserDTO logUserDTO) {
        Assert.isNull(logUserDTO);
        UserEntity userEntity = userDao.findById(logUserDTO.getUserId());
        if (userEntity == null) {
            return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (userEntity.getUpgradeBonus() == 0) {
            return RPCResult.fail(UserCodeEnum.RANK_UPGRADE_BONUS_IS_ZERO.getCode(), UserCodeEnum.RANK_UPGRADE_BONUS_IS_ZERO.getMessage());
        }

        //判断站点运营额度是否欠费
        ApiResult<OperatingLinesInfoDTO> linesInfoDTOApiResult = operatingLinesInfoService.queryLineInfoBySiteCode(userEntity.getSiteCode());
        if (!RPCResult.checkApiResult(linesInfoDTOApiResult)) {
            logger.error("获取站点运营额度异常：" + linesInfoDTOApiResult.toString());
            return linesInfoDTOApiResult;
        }
        if (linesInfoDTOApiResult.getData().getCanAmount() <= 0) {
            //return RPCResult.custom(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), PlatformCodeEnum.QUOTA_NOE_ENOUGH.getMessage());
            return RPCResult.custom(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), "领取失败，请联系客服人员");
        }

        //使用分布式锁调用出入款接口来发放奖励
        String upgradeBonusCacheKey = RedisKey.Lock.ADD_UPGRADE_BONUS + userEntity.getId();
        RedisUtil upgradeBonusRedisUtil = new RedisUtil(redisTemplate, upgradeBonusCacheKey);
        try {
            if (upgradeBonusRedisUtil.lock()) {
                IncomePayReq req = new IncomePayReq();
                req.setUserId(userEntity.getId() + "");
                req.setSiteCode(userEntity.getSiteCode());
                req.setMoney(userEntity.getUpgradeBonus().toString());
                req.setTradeType(UserConstants.TradeType.INCOME + "");
                req.setSubTradeType(UserConstants.TradeType.Income.RANK_UPGRADE_BONUS + "");
                req.setIsDemo(userEntity.getIsDemo() + "");
                req.setPayNo(OrderConstants.OrderNoPrefix.BATCH + UUID.randomUUID().toString().replace("-", "").toUpperCase());
                req.setPlatformType(logUserDTO.getPlat() + "");
                req.setPayType(UserConstant.PayType.PAY_SYSTEM + "");
                req.setRemark("用户领取晋级奖励金额");
                req.setOperTime(new Date());

                ApiResult result = userFundService.incomePay(req);
                logger.info("用户领取晋级奖励金额,入参" + req.toString() + ",返回" + result.toString());
                if (!RPCResult.checkApiResult(result)) {
                    return RPCResult.fail(result.getCode(), result.getMessage());
                }
                //入款接口调用完毕 生成用户操作日志
                ApiResult logResult = logUserService.addUserLogApi(logUserDTO);
                if (!RPCResult.checkApiResult(logResult)) {
                    return RPCResult.fail(logResult.getCode(), logResult.getMessage());
                }
                userEntity.setUpgradeBonus(0l);
                userDao.updateById(userEntity);
            }
        } catch (Exception e) {
            logger.error("领取晋级加奖出现异常:", e);
            throw new UserException(UserCodeEnum.ADD_UPGRADE_BONUS_ERROR.getCode(), UserCodeEnum.ADD_UPGRADE_BONUS_ERROR.getMessage());
        } finally {
            upgradeBonusRedisUtil.unlock();
        }
        return RPCResult.success();
    }

    /**
     * 获取站点的等级跳级信息与每日加奖信息
     *
     * @param siteId
     * @return
     */
    @Override
    public ApiResult<RankBonusConfigDTO> getSiteRankInfoBonus(Long siteId) {
        Assert.isNull(siteId);
        RankBonusConfigEntity rankBonusParam = new RankBonusConfigEntity();
        rankBonusParam.setSiteId(siteId);
        RankBonusConfigEntity entity = rankBonusConfigDao.selectOne(new QueryWrapper<>(rankBonusParam));

        RankBonusConfigDTO configDTO = new RankBonusConfigDTO();
        BeanUtil.copyProperties(entity, configDTO);

        configDTO.setUserRankDTOList(queryRankListBySiteId(siteId));
        return RPCResult.success(configDTO);
    }


}