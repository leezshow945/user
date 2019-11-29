package com.jq.user.oldbonus.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.dto.UserBonusMainSonDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.jq.user.bonus.service.UserBonusService;
import com.jq.user.bonus.service.UserBonusSettingService;
import com.jq.user.bonus.service.UserBonusSonService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.BonusConstant;
import com.jq.user.constant.SignStatus;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.oldbonus.dao.UserBonusMainDao;
import com.jq.user.oldbonus.dao.UserBonusSettingDao;
import com.jq.user.oldbonus.dao.UserBonusSonDao;
import com.jq.user.oldbonus.entity.UserBonusMainEntity;
import com.jq.user.oldbonus.entity.UserBonusSettingEntity;
import com.jq.user.oldbonus.entity.UserBonusSonEntity;
import com.jq.user.oldbonus.service.UserBonusMainInnerService;
import com.jq.user.oldbonus.service.UserBonusSonInnerService;
import com.jq.user.support.PageUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.common.util.Results;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/19
 */
@Service
public class UserBonusMainServiceImpl implements UserBonusMainInnerService {


    private static final Logger logger = LoggerFactory.getLogger(UserBonusMainServiceImpl.class);


    @Autowired
    private UserBonusMainDao userBonusMainDao;
    @Autowired
    private UserBonusSonDao userBonusSonDao;
    @Autowired
    private UserBonusSonService userBonusSonService;
    @Autowired
    private UserBonusSettingService bonusSettingService;
    @Autowired
    private UserBonusService userBonusService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBonusSonInnerService userBonusSonInnerService;
    @Autowired
    private LogUserInnerService logUserInnerService;
    @Resource
    private StringRedisTemplate template;
    @Autowired
    private UserBonusSettingDao userBonusSettingDao;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




    @Override
    public ApiResult<List<Map<String, Object>>> queryUserBonusSetByLevelApi(Integer rebateLevel, Integer settingType, boolean flag, Long siteId) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rebateLevel", rebateLevel);
            map.put("settingType", settingType);
            map.put("flag", flag);
            map.put("siteId", siteId);
            List<Map<String, Object>> userBonusMainDTOS = userBonusMainDao.queryBonusSetByLevel(map);
            return RPCResult.success(userBonusMainDTOS);
    }

    @Override
    public ApiResult<UserBonusMainDTO> getByIdApi(Long id) {
            UserBonusMainEntity userBonusMainEntity = userBonusMainDao.selectById(id);
            UserBonusMainDTO dto = new UserBonusMainDTO();
            BeanUtil.copyProperties(userBonusMainEntity, dto);
            return RPCResult.success(dto);
    }

    @Override
    @Transactional
    public ApiResult updateApi(UserBonusMainDTO dto) {
        try{
            UserBonusMainEntity entity = new UserBonusMainEntity();
            BeanUtil.copyProperties(dto, entity);
            logger.debug(String.format("更新契约主单对象: dto: %s,createTime: %s,updateTime: %s",dto.toString(),dto.getCreateTime(),dto.getUpdateTime()));
            entity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dto.getCreateTime()));
            logger.debug(String.format("更新契约主单: id:%s",dto.getId()));
            logger.debug(String.format("更新契约主单对象: entity: %s,createTime: %s,updateTime: %s",entity.toString(),entity.getCreateTime(),entity.getUpdateTime()));
            boolean update = userBonusMainDao.updateAllColumnById(entity) > 0;
            logger.info(String.format("更新契约主单: id:%s,结果:%s",dto.getId(),update));
            String content = "修改: 代理分红设置 ";
            if (update)
                logUserInnerService.addUserLog(null, dto.getUpdateBy(), UserConstant.PC, content, UserCfg.UPDATE, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
            return update ? RPCResult.success() : RPCResult.fail();
    } catch (Exception e) {
        logger.error("error",e);
        throw new UserException(ErrorCode.DEFAULT_CODE,ErrorCode.DEFAULT_MSG);
    }

}

    @Override
    @Transactional
    public ApiResult saveApi(UserBonusMainDTO dto) {
        try{
            if (dto == null)
                return RPCResult.fail();
            UserBonusMainEntity entity = new UserBonusMainEntity();
            BeanUtil.copyProperties(dto, entity);
            entity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dto.getCreateTime()));
            boolean insert = userBonusMainDao.insert(entity) > 0;
            String content = "新增: 代理日工资设置 ";
            if (insert&&UserConstant.IS_F.equals(entity.getIsDel()))
                logUserInnerService.addUserLog(null, dto.getCreateBy(), UserConstant.PC, content, UserCfg.ADD, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
            return insert ? RPCResult.success(entity.getId()) : RPCResult.fail();
    } catch (Exception e) {
        logger.error("error",e);
        throw new UserException(ErrorCode.DEFAULT_CODE,ErrorCode.DEFAULT_MSG);
    }

}

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResult editOrSaveApi(UserBonusMainSonDTO mainSonDTO) {
            //校验参数
            ApiResult apiResult = validUserBonusMain(mainSonDTO);
            if(!Results.isSuccess(apiResult)){
                return apiResult;
            }

            UserBonusMainEntity bonusMain = new UserBonusMainEntity();
            UserBonusMainEntity bonusMainBack  = new UserBonusMainEntity();
            Long newId = IdWorker.getId();
            List<UserBonusSonEntity> bonusSonList = new ArrayList<UserBonusSonEntity>();
            if (StringUtils.isNotEmpty(mainSonDTO.getId())) {
                // 根据主键查询主单
                bonusMainBack = userBonusMainDao.selectById(Long.parseLong(mainSonDTO.getId()));
                if (bonusMainBack == null) {
                    return RPCResult.fail(ErrorCode.DEFAULT_CODE, "获取主单数据失败");
                }
                // 复制契约分红主单历史记录
                BeanUtil.copyProperties(bonusMainBack,bonusMain);
                bonusMainBack.setId(newId);
                bonusMainBack.setIsDel(UserConstant.IS_T);
                bonusMainBack.setUpdateBy(mainSonDTO.getSysUserName());
                bonusMainBack.setUpdateTime(new Date());

                // 获取主单对应细单
                if (bonusMain.getSettingType() == BonusConstant.BonusSettingType.BONUS) {
                    bonusSonList = userBonusSonDao.queryUserBonusSon(Long.parseLong(mainSonDTO.getId()), "0", "", "desc");

                } else if (bonusMain.getSettingType() == BonusConstant.BonusSettingType.CONTRACT_BONUS) {
                    bonusSonList = userBonusSonDao.queryUserBonusSon(Long.parseLong(mainSonDTO.getId()), "", "0", "desc");
                }
                //查看用户是否进行过代理迁移，有的话修改lot_user_bonus表对应待结算列表的is_del状态
                UserEntity user = userDao.findById(bonusMain.getToUserId());
                if (user != null) {
                    if (null != user.getTransferTime()) {
                        ApiResult<List<UserBonusDTO>> listApiResult = userBonusService.queryUserBonusByUserIdApi(user.getId(), user.getSiteId(), false, BonusConstant.BonusSettingType.CONTRACT_BONUS);
                        List<UserBonusDTO> bonusList = listApiResult.getData();
                        if (CollectionUtil.isNotEmpty(bonusList)) {
                            for (UserBonusDTO userBonus : bonusList) {
                                userBonus.setIsDel(UserConstant.IS_F);
                                userBonus.setUpdateBy(mainSonDTO.getSysUserName());
                                userBonus.setIp(mainSonDTO.getIp());
                                userBonusService.updateApi(userBonus);

                            }
                        }
                    }
                }
                bonusMain.setUpdateBy(mainSonDTO.getSysUserName());
                bonusMain.setUpdateTime(new Date());
            } else {
                bonusMain.setCreateBy(mainSonDTO.getSysUserName());
                bonusMain.setCreateTime(new Date());
                bonusMain.setSiteId(mainSonDTO.getSiteId());
                bonusMain.setSiteCode(mainSonDTO.getSiteCode());
                bonusMain.setId(IdWorker.getId());
            }

            bonusMain.setPlayType(JSONUtil.toJsonStr(mainSonDTO.getGameList()));
            bonusMain.setRebateLevel(mainSonDTO.getRebateLevel());
            bonusMain.setGameCategoryId(mainSonDTO.getGameCategoryId());
            bonusMain.setDistribute(mainSonDTO.getDistribute());
            bonusMain.setBonusCycle(mainSonDTO.getBonusCycle());
            bonusMain.setBonusRule(mainSonDTO.getBonusRule());
            bonusMain.setBonusStrategy(mainSonDTO.getBonusStrategy());
            bonusMain.setSettingType(mainSonDTO.getSettingType());
            bonusMain.setIsDel(UserConstant.IS_F);
            logger.info(String.format("bonusMain入库对象 = %s", bonusMain));
            String cz = "";
            String czType = "";
            String mainLevel = "";
            String content = "";
            String bonusType = "";
            String bonusUser = "";
            if (bonusMain.getSettingType().equals(BonusConstant.BonusSettingType.BONUS)) {
                bonusType = "分红规则。";
                mainLevel = "层级：" + bonusMain.getRebateLevel() + "。";
            } else if (bonusMain.getSettingType().equals(BonusConstant.BonusSettingType.CONTRACT_BONUS)) {
                bonusType = "契约分红规则。";
                UserEntity user = userDao.findById(bonusMain.getUserId());
                UserEntity toUser = userDao.findById(bonusMain.getToUserId());
                if(null == user){
                    throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "创建用户不存在");
                }else if(null == toUser){
                    throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "契约用户不存在");
                }
                bonusUser = "创建用户：" + user.getUserName() + "，契约用户：" + toUser.getUserName();
            }

            if (StringUtils.isNotEmpty(mainSonDTO.getId())) {
                logger.info(String.format("修改代理分红规则主单 param：%s", JSONUtil.toJsonStr(bonusMain)));
                bonusMain.setSignStatus(BonusConstant.SignStatus.NOT_SIGN);
                bonusMain.setCreateTime(new Date());
                bonusMain.setSignTime(null);

                // 周期变化，设置新的结算日期
                if (bonusMainBack.getBonusCycle() != mainSonDTO.getBonusCycle()) {
                    bonusMain.setSettleTime(new Date());
                }
                //备份：历史主单状态
                userBonusMainDao.insert(bonusMainBack);
                logger.info(String.format("保存代理分红主单历史记录 param：%s", JSONUtil.toJsonStr(bonusMainBack)));

                userBonusMainDao.updateById(bonusMain);
                logger.info(String.format("修改代理分红主单记录 param：%s", JSONUtil.toJsonStr(bonusMain)));

                content = "修改：" + bonusType + cz + czType + mainLevel + bonusUser;
                logUserInnerService.addUserLog(mainSonDTO.getSysUserId(), mainSonDTO.getSysUserName(), UserConstant.PC, content, UserCfg.UPDATE, mainSonDTO.getOperatorType(), mainSonDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, mainSonDTO.getIp(), null);
            } else {
                logger.info(String.format("新增代理分红规则主单 param：%s", JSONUtil.toJsonStr(bonusMain)));
                userBonusMainDao.insert(bonusMain);
                content = "新增：" + bonusType + cz + czType + mainLevel + bonusUser;
                logUserInnerService.addUserLog(mainSonDTO.getSysUserId(), mainSonDTO.getSysUserName(), UserConstant.PC, content, UserCfg.ADD, mainSonDTO.getOperatorType(), mainSonDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, mainSonDTO.getIp(), null);

            }

            // 保存原细单，新细单数据
            List<UserBonusSonDTO> sonDTOList = new ArrayList<>();
            List<Map<String,String>> time = new ArrayList<>();
            //针对不同游戏大类进行设定去重校验
            for (int i = 0; i < mainSonDTO.getValidMember().length; i++) {
                if(mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.WIN_LOSE ){
                    if(CollectionUtil.isNotEmpty(time)){
                        for (Map<String, String> map : time) {
                            if(MapUtil.isNotEmpty(map)){
                                if(mainSonDTO.getValidMember()[i].equals(map.get("valid_member")) && mainSonDTO.getActualWinLose()[i].equals(map.get("actual_winLose"))){
                                    throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "已存在和第" + (i + 1) + "条一样的细单信息!");
                                }
                            }
                        }
                    }

                    Map<String,String> checkMap = new HashMap<>();
                    checkMap.put("valid_member",mainSonDTO.getValidMember()[i]);
                    checkMap.put("actual_winLose",mainSonDTO.getActualWinLose()[i]);
                    time.add(checkMap);
                }else {
                    if(CollectionUtil.isNotEmpty(time)){
                        for (Map<String, String> map : time) {
                            if(MapUtil.isNotEmpty(map)){
                                if(mainSonDTO.getValidMember()[i].equals(map.get("valid_member")) && mainSonDTO.getActualWinLose()[i].equals(map.get("actual_winLose")) && mainSonDTO.getAmount()[i].equals(map.get("amount"))){
                                    throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "已存在和第" + (i + 1) + "条一样的细单信息!");
                                }
                            }
                        }
                    }

                    Map<String,String> checkMap = new HashMap<>();
                    checkMap.put("valid_member",mainSonDTO.getValidMember()[i]);
                    checkMap.put("amount",mainSonDTO.getAmount()[i]);
                    checkMap.put("actual_winLose",mainSonDTO.getActualWinLose()[i]);
                    time.add(checkMap);
                }

                int mode = NumberUtils.toInt(mainSonDTO.getBonusMode()[i]);

                UserBonusSonEntity bonusSon = new UserBonusSonEntity();
                UserBonusSonEntity bonusSonBack = new UserBonusSonEntity();

                if(i <= mainSonDTO.getSonId().length-1 && StringUtils.isNotEmpty(mainSonDTO.getSonId()[i])){
                    bonusSonBack = userBonusSonDao.selectById(mainSonDTO.getSonId()[i]);
                    if(null == bonusSonBack){
                        throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "该子单已经被删除");
                    }
                    BeanUtil.copyProperties(bonusSonBack,bonusSon);
//                    bonusSon.setId(IdWorker.getId());
                    bonusSonBack.setId(IdWorker.getId());
                    bonusSonBack.setIsDel(UserConstant.IS_T);
                    bonusSonBack.setUpdateBy(mainSonDTO.getSysUserName());
                    bonusSonBack.setUpdateTime(new Date());
                }

                if(mainSonDTO.getBonusRule() != BonusConstant.BonusRuleDetail.WIN_LOSE ){
                    bonusSon.setAmount(Long.parseLong(mainSonDTO.getAmount()[i]) * 100);
                }

                bonusSon.setActualWinLose(Long.parseLong(mainSonDTO.getActualWinLose()[i])*100);
                bonusSon.setValidMember(NumberUtils.toInt(mainSonDTO.getValidMember()[i]));
                if (mode == 0) {
                    bonusSon.setLimitAmount(Long.parseLong(mainSonDTO.getLimitAmount()[i]) * 100);
                    bonusSon.setBonusPer(Long.parseLong(String.valueOf(Math.round(Double.valueOf(mainSonDTO.getBonusPer()[i]) * 100))));
                } else if (mode == 1) {
                    bonusSon.setBonusAmount(Long.parseLong(mainSonDTO.getBonusAmount()[i]) * 100);
                }
                bonusSon.setBonusMode(mode);
                bonusSon.setMainId(bonusMain.getId());
                bonusSon.setIsDel(UserConstant.IS_F);
                logger.info(String.format("bonusSon入库对象 = %s", bonusSon));
                if (i <= mainSonDTO.getSonId().length-1 && StringUtils.isNotEmpty(mainSonDTO.getSonId()[i])) {

                    bonusSon.setUpdateBy(mainSonDTO.getSysUserName());
                    bonusSon.setUpdateTime(new Date());
                    logger.info(String.format("保存代理分红规则历史细单 param：%s", JSONUtil.toJsonStr(bonusSonBack)));
                    bonusSonBack.setMainId(newId);
                    userBonusSonDao.insert(bonusSonBack);

                    // 保存代理分红规则历史细单
                    userBonusSonDao.updateById(bonusSon);
                    logger.info(String.format("修改代理分红规则细单 param：%s", JSONUtil.toJsonStr(bonusSon)));
                } else {
                    bonusSon.setCreateBy(mainSonDTO.getSysUserName());
                    bonusSon.setCreateTime(new Date());
                    logger.info(String.format("新增代理分红规则细单 param：%s", JSONUtil.toJsonStr(bonusSon)));
                    userBonusSonDao.insert(bonusSon);
                }

                UserBonusSonDTO bonusSonDTO = new UserBonusSonDTO();
                BeanUtil.copyProperties(bonusSon, bonusSonDTO);
                sonDTOList.add(bonusSonDTO);
            }

            //将最新的规则放入缓存，结算时用
//            UserBonusMainDTO mainDTO = new UserBonusMainDTO();
//            BeanUtil.copyProperties(bonusMain, mainDTO);
//            mainDTO.setBonusSonList(sonDTOList);
//            ValueOperations<String, String> ops = template.opsForValue();
//            ops.set(RedisKey.BONUS_SETTINGTYPE + mainDTO.getSettingType() + ":" + mainDTO.getId(), JSONUtil.toJsonStr(mainDTO));
//            logger.info(String.format("bonusMain缓存对象 = %s", JSONUtil.toJsonStr(mainDTO)));

            //删除细单
            List<String> newIds = Arrays.asList(mainSonDTO.getSonId());
            for (UserBonusSonEntity bonusSonEntity : bonusSonList) {
                if(!newIds.contains(bonusSonEntity.getId().toString())){
                    bonusSonEntity.setIsDel(UserConstant.IS_T);
                    bonusSonEntity.setUpdateTime(new Date());
                    bonusSonEntity.setUpdateBy(mainSonDTO.getSysUserName());
                    boolean flag = userBonusSonDao.updateById(bonusSonEntity) > 0;
                    if (!flag) {
                        logger.info(String.format("修改分红/契约分红设置 删除子单失败 子单id=%s", bonusSonEntity.getId()));
                        throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "编辑分红设置子单失败");
                    }
                }
            }

            logger.info(String.format("修改分红/契约分红设置成功 主单id=%s",mainSonDTO.getId()));
            return RPCResult.success("修改代理分红/契约分红成功");
    }

    @Override
    @Transactional
    public ApiResult updateStatusApi(UserBonusMainSonDTO mainSonDTO) {
            if (mainSonDTO == null)
                return RPCResult.fail();
            Assert.isNull(mainSonDTO.getSonId(), "代理分红设置子单id不能为空");
            logger.info(String.format("修改代理分红细单删除状态 son= %s", mainSonDTO.getSonId()[0]));

            UserBonusSonEntity bonusSon = userBonusSonDao.selectById(mainSonDTO.getSonId()[0]);
            Assert.isNull(bonusSon, "获取代理分红细单信息异常");
            bonusSon.setUpdateBy(mainSonDTO.getSysUserName());
            bonusSon.setUpdateTime(new Date());
            bonusSon.setIsDel(UserConstant.IS_T);
            logger.info(String.format("删除代理分红细单 sonModel= %s", JSONUtil.toJsonStr(bonusSon)));

            userBonusSonDao.updateById(bonusSon);
            //判断主单下是否还有细单，没有的话要修改主单的is_del为T
            if (StringUtils.isBlank(mainSonDTO.getMainId()))
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "分红设置主单id不存在");
            List<UserBonusSonEntity> bonusList = userBonusSonDao.querySonByMainId(Long.parseLong(mainSonDTO.getMainId()),UserConstant.IS_ZERO);
            UserBonusMainEntity bonusMain = userBonusMainDao.selectById(Long.parseLong(mainSonDTO.getMainId()));
            String cz = "";
            String czType = "";
            String mainLevel = "";
            String content = "";
            String bonusType = "";
            if (bonusMain.getSettingType().equals(BonusConstant.BonusSettingType.BONUS)) {
                bonusType = "分红规则";
                mainLevel = "层级：" + bonusMain.getRebateLevel() + "。";
            } else if (bonusMain.getSettingType().equals(BonusConstant.BonusSettingType.CONTRACT_BONUS)) {
                bonusType = "契约分红规则";
            }

            if (CollectionUtil.isEmpty(bonusList)) {
                bonusMain.setUpdateBy(mainSonDTO.getSysUserName());
                bonusMain.setUpdateTime(new Date());
                bonusMain.setIsDel(UserConstant.IS_T);
                logger.info(String.format("修改代理分红主单 main= %s", JSONUtil.toJsonStr(bonusMain)));
                userBonusMainDao.updateById(bonusMain);
                content = "删除：" + bonusType + "主单。" + cz + czType + mainLevel;
                logUserInnerService.addUserLog(mainSonDTO.getSysUserId(), mainSonDTO.getSysUserName(), UserConstant.PC, content, UserCfg.DELETE, mainSonDTO.getOperatorType(), mainSonDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, mainSonDTO.getIp(), null);
            }
            content = "删除：" + bonusType + "细单。" + cz + czType + mainLevel;
            logUserInnerService.addUserLog(mainSonDTO.getSysUserId(), mainSonDTO.getSysUserName(), UserConstant.PC, content, UserCfg.DELETE, mainSonDTO.getOperatorType(), mainSonDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, mainSonDTO.getIp(), null);
            return RPCResult.success();
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusInfoApi(UserBonusMainDTO dto) {
            Assert.isNull(dto.getSiteId(), "站点id不能为空");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("siteId",dto.getSiteId());
            paramMap.put("gameCategoryId",dto.getGameCategoryId());
            paramMap.put("playType",dto.getPlayType());
            paramMap.put("toUserName",dto.getToUserName());
            paramMap.put("userName",dto.getUserName());
            paramMap.put("settingType",dto.getSettingType());
            paramMap.put("isDel",dto.getIsDel());
            paramMap.put("signStatus",dto.getSignStatus());
            paramMap.put("startTime",dto.getStartTime());
            paramMap.put("endTime",dto.getEndTime());
            Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit());
            List<Map<String, Object>> list = userBonusMainDao.queryContractBonusInfo(paramMap,page);
            PageInfo<Map<String, Object>> listPageInfo = new PageInfo<>(list, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
            return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusApi(UserBonusMainDTO dto) {
            Assert.isNull(dto.getSiteId(), "站点id不能为空");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("siteId",dto.getSiteId());
            paramMap.put("gameCategoryId",dto.getGameCategoryId());
            paramMap.put("playType",dto.getPlayType());
            paramMap.put("toUserName",dto.getToUserName());
            paramMap.put("userName",dto.getUserName());
            paramMap.put("settingType",dto.getSettingType());
            paramMap.put("isDel",dto.getIsDel());
            Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit());
            List<Map<String, Object>> list = userBonusMainDao.queryContractBonus(paramMap, page);
            PageInfo<Map<String, Object>> listPageInfo = new PageInfo<>(list, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
            return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> queryContractMainSonByIdApi(Long mainId, Long siteId) {
            List<Map<String, Object>> list = userBonusMainDao.queryContractMainSonById(mainId, siteId);
            return RPCResult.success(list);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractWageInfoApi(Long siteId, int pageNum, int pageSize, String gameCategoryId, String playType, String toUserName, String userName) {
            UserBonusMainDTO dto = new UserBonusMainDTO();
            dto.setSiteId(siteId);
            dto.setPage(pageNum);
            dto.setLimit(pageSize);
            dto.setGameCategoryId(gameCategoryId);
            dto.setPlayType(playType);
            dto.setToUserName(toUserName);
            dto.setUserName(userName);
            dto.setSettingType(3);
            ApiResult<PageInfo<Map<String, Object>>> pageInfoApiResult = queryContractBonusInfoApi(dto);
            return pageInfoApiResult;
    }


    @Override
    public ApiResult<PageInfo<UserBonusMainDTO>> queryContractRecordByUserIdApi(UserBonusMainDTO queryDTO) {
            Assert.isNull(queryDTO.getUserId(), "用户id 不存在");
            Assert.isNull(queryDTO.getSiteId(), "站点信息异常");
            QueryWrapper<UserBonusMainEntity> ew = new QueryWrapper<>();
            ew.eq("to_user_id", queryDTO.getUserId());
            ew.eq("setting_type", queryDTO.getSettingType());
            ew.eq("site_id", queryDTO.getSiteId());
            ew.eq(queryDTO.getSignStatus()!=null,"sign_status", queryDTO.getSignStatus());
            ew.eq(StrUtil.isNotEmpty(queryDTO.getGameCategoryId()),"game_category_id", queryDTO.getGameCategoryId());
            ew.ge(StrUtil.isNotEmpty(queryDTO.getStartTime()),"sign_time", queryDTO.getStartTime());
            ew.le(StrUtil.isNotEmpty(queryDTO.getEndTime()),"sign_time", queryDTO.getEndTime());
            ew.eq(queryDTO.getIsDel()!=null, "is_del",queryDTO.getIsDel());
            Page page = PageUtil.buildPage(queryDTO.getPage(), queryDTO.getLimit(), queryDTO.getOrderDirection(), queryDTO.getOrderField());
            IPage<UserBonusMainEntity> iPage = userBonusMainDao.selectPage(page, ew);
            List<UserBonusMainDTO> dtoList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(iPage.getRecords())) {
                for (UserBonusMainEntity entity : iPage.getRecords()) {
                    UserBonusMainDTO dto = new UserBonusMainDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setCreateTime(DateUtil.formatDateTime(entity.getCreateTime()));
                    dto.setUpdateTime(DateUtil.formatDateTime(entity.getUpdateTime()));
                    if (BonusConstant.BonusSettingType.CONTRACT_BONUS == dto.getSettingType()) {
                        List<UserBonusSonEntity> bonusSonList = userBonusSonDao.querySonByMainId(entity.getId(),queryDTO.getIsDel());
                        //放入子单
                        if (CollectionUtil.isNotEmpty(bonusSonList)) {
                            List<UserBonusSonDTO> sonDTOList = new ArrayList<>();
                            for (UserBonusSonEntity bonusSonEntity : bonusSonList) {
                                UserBonusSonDTO sonDTO = new UserBonusSonDTO();
                                BeanUtils.copyProperties(bonusSonEntity, sonDTO);
                                sonDTOList.add(sonDTO);
                            }
                            dto.setBonusSonList(sonDTOList);
                        }
                    } else if (BonusConstant.BonusSettingType.CONTRACT_DAILY == dto.getSettingType()) {
                        List<UserBonusSettingEntity> bonusSettingList = userBonusSettingDao.querySettingByMainId(entity.getId(),queryDTO.getIsDel());
                        //放入子单
                        if (CollectionUtil.isNotEmpty(bonusSettingList)) {
                            List<UserBonusSettingDTO> settingDTOList = new ArrayList<>();
                            for (UserBonusSettingEntity bonusSettingEntity : bonusSettingList) {
                                UserBonusSettingDTO settingDTO = new UserBonusSettingDTO();
                                BeanUtils.copyProperties(bonusSettingEntity, settingDTO);
                                settingDTOList.add(settingDTO);
                            }
                            dto.setBonusSettingList(settingDTOList);
                        }
                    }

                    dtoList.add(dto);
                }
            }
            PageInfo<UserBonusMainDTO> pageInfo = new PageInfo<>(dtoList,(int)page.getCurrent(),(int)page.getSize(),page.getTotal());
            return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> queryContractRecordApi(UserBonusMainDTO dto) {
            if (dto == null)
                return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(), UserCodeEnum.PARAM_IS_NULL.getMessage());
            if (dto.getSettingType() == null)
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "分红设置类行不能为空");
            if (dto.getUserId() == null)
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "发起用户id不能为空");
            if (dto.getSiteId() == null)
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "站点id不能为空");
            UserBonusMainEntity entity = new UserBonusMainEntity();
            BeanUtil.copyProperties(dto, entity);
            List<UserBonusMainEntity> list = userBonusMainDao.queryContractRecord(entity);
            List<UserBonusMainDTO> dtoList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(list)) {
                for (UserBonusMainEntity userBonusMainEntity : list) {
                    UserBonusMainDTO mainDTO = new UserBonusMainDTO();
                    BeanUtil.copyProperties(userBonusMainEntity, mainDTO);
                    dtoList.add(mainDTO);
                }
            }
            return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> queryMain(Long siteId, Integer settingType, Integer signStatus) {
            QueryWrapper<UserBonusMainEntity> ew = new QueryWrapper<>();
            ew.eq("site_id", siteId);
            ew.eq("setting_type", settingType);
            ew.eq("is_del", UserConstant.IS_F);
            ew.eq(settingType == 1 || settingType == 3,"sign_status", signStatus);
            List<UserBonusMainEntity> userBonusMainEntities = userBonusMainDao.selectList(ew);
            List<UserBonusMainDTO> dtoList = new ArrayList<>();
            for (UserBonusMainEntity userBonusMainEntity : userBonusMainEntities) {
                UserBonusMainDTO dto = new UserBonusMainDTO();
                BeanUtil.copyProperties(userBonusMainEntity, dto);
                dtoList.add(dto);
            }
            return RPCResult.success(dtoList);
    }


    @Override
    public ApiResult<List<UserBonusMainDTO>> queryBonusMainSonApi(Long siteId, Integer settingType, Integer signStatus) {
            QueryWrapper<UserBonusMainEntity> ew = new QueryWrapper<>();
            if (settingType != null)
                ew.eq("setting_type", settingType);
            if (siteId != null)
                ew.eq("site_id", siteId);
            ew.eq("is_del", UserConstant.IS_F);
            List<UserBonusMainEntity> bonusMainList = userBonusMainDao.selectList(ew);
            List<UserBonusMainDTO> dtoList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(bonusMainList)) {
                for (UserBonusMainEntity entity : bonusMainList) {
                    UserBonusMainDTO dto = new UserBonusMainDTO();
                    BeanUtils.copyProperties(entity, dto);
                    if (BonusConstant.BonusSettingType.CONTRACT_BONUS == settingType || BonusConstant.BonusSettingType.BONUS == settingType) {
                        ApiResult<List<UserBonusSonDTO>> userBonusSonApi = userBonusSonService.getUserBonusSonApi(settingType, entity.getId());
                        dto.setBonusSonList(userBonusSonApi.getData());
                    } else if (BonusConstant.BonusSettingType.CONTRACT_DAILY == settingType || BonusConstant.BonusSettingType.DAILY == settingType) {
                        ApiResult<List<UserBonusSettingDTO>> bonusSettingApi = bonusSettingService.getBonusSettingApi(settingType, entity.getId());
                        dto.setBonusSettingList(bonusSettingApi.getData());
                    }
                    dtoList.add(dto);
                }
            }
            return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> getBonusMainApi(Integer settingType, List<Integer> levelList, List<Long> toUserIdList, Long siteId) {
            if (settingType == null || (settingType != 0 && settingType != 1)) {
                throw new UserException(UserCodeEnum.BONUS_TYPE_ILLEGAL.getCode());
            }
            QueryWrapper<UserBonusMainEntity> ew = new QueryWrapper<>();
            ew.in("rebate_level", levelList);
            ew.in("to_user_id", toUserIdList);
            ew.eq("setting_type", settingType);
            ew.eq("site_id", siteId);
            // 类型为分红时,签订状态为:未签订
            ew.eq(settingType == 0, "sign_status", SignStatus.UN_SIGN.getCode());
            // 类型为契约分红时,签订状态为:已签订
            ew.eq(settingType == 1, "sign_status", SignStatus.AGREE.getCode());
            List<UserBonusMainEntity> userBonusMainEntities = userBonusMainDao.selectList(ew);
            List<UserBonusMainDTO> dtoList = new ArrayList<>();
            for (UserBonusMainEntity userBonusMainEntity : userBonusMainEntities) {
                UserBonusMainDTO dto = new UserBonusMainDTO();
                BeanUtil.copyProperties(userBonusMainEntity, dto);
                dtoList.add(dto);
            }
            return RPCResult.success(dtoList);
    }

    @Override
    @Transactional
    public ApiResult<Boolean> updateSettleTime(Long id, Date date) {
            UserBonusMainEntity userBonusMainEntity = new UserBonusMainEntity();
            userBonusMainEntity.setId(id);
            userBonusMainEntity.setSettleTime(date);
            userBonusMainEntity.setUpdateTime(new Date());
            Integer result = userBonusMainDao.updateById(userBonusMainEntity);
            return RPCResult.success(result>0);
    }


    @Override
    public ApiResult<List<UserBonusMainDTO>> batchQueryUserBonusMain(List<Long> ids,Integer settingType) {
        if(CollectionUtil.isEmpty(ids) || null == settingType){
            logger.warn("批量查询奖金设置失败 参数异常");
            return RPCResult.fail();
        }
        List<UserBonusMainDTO> dtoList = new ArrayList<>();
        for (Long id : ids) {
            UserBonusMainDTO main = new UserBonusMainDTO();
            main.setId(id);
            dtoList.add(main);
        }
        if(BonusConstant.BonusSettingType.BONUS == settingType || BonusConstant.BonusSettingType.CONTRACT_BONUS == settingType){
            QueryWrapper<UserBonusSonEntity> ew = new QueryWrapper<>();
            ew.in("main_id", ids);
            ew.eq("is_del", UserConstant.IS_F);
            List<UserBonusSonEntity> bonusSonEntityList = userBonusSonDao.selectList(ew);
            for (UserBonusSonEntity bonusSonEntity : bonusSonEntityList) {
                for (UserBonusMainDTO bonusMainDTO : dtoList) {
                    if(bonusMainDTO.getId().longValue() == bonusSonEntity.getMainId().longValue()){
                        List<UserBonusSonDTO> bonusSonList = bonusMainDTO.getBonusSonList();
                        if(CollectionUtil.isEmpty(bonusSonList)){
                            bonusSonList = new ArrayList<>();
                        }
                        UserBonusSonDTO sonDTO = new UserBonusSonDTO();
                        BeanUtil.copyProperties(bonusSonEntity,sonDTO);
                        bonusSonList.add(sonDTO);
                        bonusMainDTO.setBonusSonList(bonusSonList);
                    }
                }
            }

        }else if(BonusConstant.BonusSettingType.DAILY == settingType || BonusConstant.BonusSettingType.CONTRACT_DAILY == settingType){

            QueryWrapper<UserBonusSettingEntity> ew = new QueryWrapper<>();
            ew.in("main_id", ids);
            ew.eq("is_del", UserConstant.IS_F);
            List<UserBonusSettingEntity> bonusSonEntityList = userBonusSettingDao.selectList(ew);
            for (UserBonusSettingEntity settingEntity : bonusSonEntityList) {
                for (UserBonusMainDTO bonusMainDTO : dtoList) {
                    if(bonusMainDTO.getId().longValue() == settingEntity.getMainId().longValue()){
                        List<UserBonusSettingDTO> settingDTOList = bonusMainDTO.getBonusSettingList();
                        if(CollectionUtil.isEmpty(settingDTOList)){
                            settingDTOList = new ArrayList<>();
                        }
                        UserBonusSettingDTO sonDTO = new UserBonusSettingDTO();
                        BeanUtil.copyProperties(settingEntity,sonDTO);
                        settingDTOList.add(sonDTO);
                        bonusMainDTO.setBonusSettingList(settingDTOList);
                    }
                }
            }
        }

        return RPCResult.success(dtoList);

    }


    private ApiResult validUserBonusMain(UserBonusMainSonDTO mainSonDTO){
        Pattern pat = Pattern.compile("[0-9]+");
        Pattern pat2 = Pattern.compile("^100$|^100.0$|^(\\d|[1-9]\\d)(\\.[0-9]{1})*$");
        Pattern pat1 = Pattern.compile("^100$|^100.0$|^100.00$|^(\\d|[1-9]\\d)(\\.[0-9]{1})*$|^(\\d|[1-9]\\d)(\\.[0-9]{2})*$");
//        if (StringUtils.isEmpty(mainSonDTO.getGameCategoryId())) {
//            logger.info("未获取到游戏分类gameCategoryId");
//            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "未获取到游戏分类gameCategoryId");
//        }
        if (mainSonDTO.getSettingType() == BonusConstant.BonusSettingType.BONUS || mainSonDTO.getSettingType() == BonusConstant.BonusSettingType.DAILY) {
            if (mainSonDTO.getRebateLevel() == null) {
                logger.warn(String.format("未获取到层级参数 :rebateLevel={%s}",mainSonDTO.getRebateLevel()));
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "未获取到层级参数rebateLevel");
            }
        }
        if (mainSonDTO.getDistribute() == null || mainSonDTO.getBonusRule() == null || mainSonDTO.getBonusMode() == null || mainSonDTO.getBonusMode().length <= 0 || mainSonDTO.getBonusStrategy() == null || mainSonDTO.getBonusCycle() == null || mainSonDTO.getValidMember() == null || mainSonDTO.getValidMember().length <= 0) {
            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getMessage());
        }
        if (mainSonDTO.getSettingType() == BonusConstant.BonusSettingType.BONUS) {
//            if (mainSonDTO.getRebate() == null || mainSonDTO.getRebate().length <= 0) {
//                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getMessage());
//            }
//            for (int i = 0; i < mainSonDTO.getRebate().length; i++) {
//                if (mainSonDTO.getRebate()[i].length() <= 0 || !pat2.matcher(mainSonDTO.getRebate()[i]).matches()) {
//                    logger.info("返点要求应填0-100，可带1位小数:rebate");
//                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "返点要求应填0-100，可带1位小数:rebate");
//                } else if (mainSonDTO.getRebate()[i].length() > 12) {
//                    logger.info("返点要求不能大于12位数:rebate");
//                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "返点要求不能大于12位数:rebate");
//                }
//            }
            if (mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE || mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET) {
                for (int i = 0; i < mainSonDTO.getValidMember().length; i++) {
                    if (mainSonDTO.getAmount()[i].length() <= 0 || !pat.matcher(mainSonDTO.getAmount()[i]).matches()) {
                        if (mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE) {
                            logger.warn(String.format("团队日量应填正整数:daysAllAmount={%s}",mainSonDTO.getAmount()));
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队日量应填正整数");
                        } else if (mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET) {
                            logger.warn(String.format("团队总量应填正整数:daysAllAmount={%s}",mainSonDTO.getAmount()));
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队日量应填正整数");
                        }
                    } else if (mainSonDTO.getAmount()[i].length() > 12) {
                        logger.warn(String.format("团队总量/日量不能大于12位数:amount={%s}",mainSonDTO.getAmount()));
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队总量/日量不能大于12位数");
                    }
                }
            }
        } else if (mainSonDTO.getSettingType() == BonusConstant.BonusSettingType.CONTRACT_BONUS) {
            if(mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE || mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET){
                if (mainSonDTO.getAmount() == null || mainSonDTO.getAmount().length <= 0) {
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getMessage());
                }
                for (int i = 0; i < mainSonDTO.getValidMember().length; i++) {
                    String temp = mainSonDTO.getAmount()[i].toString();
                    if (temp.length() <= 0 || !pat.matcher(temp).matches()) {
                        if (mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE) {
                            logger.warn(String.format("团队日量应填正整数:daysAllAmount={%s}",mainSonDTO.getAmount()));
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队日量应填正整数");
                        } else if (mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET) {
                            logger.warn(String.format("团队总量应填正整数:daysAllAmount={%s}",mainSonDTO.getAmount()));
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队日量应填正整数");
                        }
                    } else if (temp.length() > 12) {
                        logger.warn(String.format("团队总量/日量不能大于12位数:amount={%s}",mainSonDTO.getAmount()));
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "团队总量/日量不能大于12位数");
                    }
                }
            }
        }

        for (int i = 0; i < mainSonDTO.getValidMember().length; i++) {
            if (mainSonDTO.getValidMember()[i].length() <= 0 || !pat.matcher(mainSonDTO.getValidMember()[i]).matches()) {
                logger.warn("有效会员应填正整数:validMember");
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "有效会员应填正整数");
            } else if (mainSonDTO.getValidMember()[i].length() > 12) {
                logger.warn("有效会员不能大于12位数:validMember");
                return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "有效会员不能大于12位数");
            }
        }
        for (int i = 0; i < mainSonDTO.getValidMember().length; i++) {
            int mode = NumberUtils.toInt(mainSonDTO.getBonusMode()[i]);
            if (mode == 0) {
                if(mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE || mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET){
                    if("0".equals(mainSonDTO.getActualWinLose()[i]) && "0".equals(mainSonDTO.getAmount()[i])&&"0".equals(mainSonDTO.getValidMember()[i])&&"0".equals(mainSonDTO.getBonusPer()[i])&&"0".equals(mainSonDTO.getLimitAmount()[i])){
                        logger.warn("都为0 为无效条件");
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                    }
                }else{
                    if("0".equals(mainSonDTO.getActualWinLose()[i]) && "0".equals(mainSonDTO.getValidMember()[i])&&"0".equals(mainSonDTO.getBonusPer()[i])&&"0".equals(mainSonDTO.getLimitAmount()[i])){
                        logger.warn("都为0 为无效条件");
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                    }
                }

                if ("".equals(mainSonDTO.getLimitAmount()[i]) || !pat.matcher(mainSonDTO.getLimitAmount()[i]).matches()) {
                    logger.warn("上限金额应填正整数:limitAmount");
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "上限金额应填正整数");
                } else if (mainSonDTO.getLimitAmount()[i].length() > 12) {
                    logger.warn("上限金额不能大于12位数:limitAmount");
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "上限金额不能大于12位数");
                }
                if ("".equals(mainSonDTO.getBonusPer()[i]) || !pat1.matcher(mainSonDTO.getBonusPer()[i]).matches()) {
                    logger.warn("奖金比例应填0-100，可带2位小数:bonusPer");
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "奖金比例应填0-100，可带2位小数");
                }
            } else if (mode == 1) {
                if(mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.DAILY_AVERAGE || mainSonDTO.getBonusRule() == BonusConstant.BonusRuleDetail.TOTAL_BET){
                    if("0".equals(mainSonDTO.getActualWinLose()[i]) && "0".equals(mainSonDTO.getAmount()[i])&&"0".equals(mainSonDTO.getValidMember()[i])&&"0".equals(mainSonDTO.getBonusAmount()[i])){
                        logger.warn("都为0 为无效条件");
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                    }
                }else{
                    if("0".equals(mainSonDTO.getActualWinLose()[i]) && "0".equals(mainSonDTO.getValidMember()[i])&&"0".equals(mainSonDTO.getBonusAmount()[i])){
                        logger.warn("都为0 为无效条件");
                        return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                    }
                }
                if ("".equals(mainSonDTO.getBonusAmount()[i]) || !pat.matcher(mainSonDTO.getBonusAmount()[i]).matches()) {
                    logger.warn("奖金金额应填正整数:bonusAmount");
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "奖金金额应填正整数");
                } else if (mainSonDTO.getBonusAmount()[i].length() > 12) {
                    logger.warn("奖金金额不能大于12位数:bonusAmount");
                    return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "奖金金额不能大于12位数");
                }
            }
        }
        return Results.success();
    }


}
