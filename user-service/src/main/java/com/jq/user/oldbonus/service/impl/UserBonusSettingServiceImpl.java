package com.jq.user.oldbonus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.bonus.dto.UserBonusMainSettingDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.BonusConstant;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.SysUserDao;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.SysUserEntity;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.oldbonus.dao.UserBonusMainDao;
import com.jq.user.oldbonus.dao.UserBonusSettingDao;
import com.jq.user.oldbonus.entity.UserBonusMainEntity;
import com.jq.user.oldbonus.entity.UserBonusSettingEntity;
import com.jq.user.oldbonus.service.UserBonusSettingInnerService;
import com.liying.common.PageInfo;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.common.util.Results;
import com.liying.game.api.GameCategoryService;
import com.liying.game.api.req.QueryGameCategoryReq;
import com.liying.game.api.resp.GameCategoryResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserBonusSettingServiceImpl implements  UserBonusSettingInnerService {

    private static final Logger logger = LoggerFactory.getLogger(UserBonusMainServiceImpl.class);
    @Resource
    private UserBonusSettingDao userBonusSettingDao;
    @Resource
    private GameCategoryService gameCategoryService;
    @Resource
    private KeyValueService keyValueService;
    @Resource
    private UserBonusMainDao userBonusMainDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private StringRedisTemplate template;

    @Override
    public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(Long siteId, Integer level, Integer settingType) {
        QueryGameCategoryReq req = new QueryGameCategoryReq();
        req.setPid(0L);
        req.setIsEnable(1);
        ApiResult<PageInfo<GameCategoryResp>> apiResult = gameCategoryService.queryGameCategory(req);

        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode(), apiResult.getMessage());
        }
        PageInfo<GameCategoryResp> data = apiResult.getData();
        List<GameCategoryResp> cateList = new ArrayList<>();
        if (null != data) {
            cateList = data.getContent();
        }
        List<Map<String, Object>> setList = userBonusSettingDao.queryBonusSetByLevel(level, settingType, siteId);
        Map<String, Object> map = new HashMap<>();
        map.put("cateList", cateList);
        map.put("setList", setList);
        return RPCResult.success(map);
    }

    @Override
    public ApiResult<List<UserBonusSettingDTO>> getBonusSettingApi(Integer settingType, Long mainId) {
            QueryWrapper<UserBonusSettingEntity> ew = new QueryWrapper<>();
            if (mainId != null) {
                ew.eq("main_id", mainId);
            }
            ew.eq("is_del", UserConstant.IS_F);
//        if(settingType!=null)
//            ew.eq("setting_type",settingType);
//    排序：    日工资：返点要求>团队实际亏损>团队日量>有效会员
//            List<String> sortReq = new ArrayList<>();
//            if (settingType == 2) {
//                sortReq.add("rebate");
//                sortReq.add("team_actual_lose");
//                sortReq.add("team_daily_amount");
//                sortReq.add("valid_member");
//            }
//    排序    契约日工资：日量>有效会员
//            if (settingType == 3) {
//                sortReq.add("team_daily_amount");
//                sortReq.add("valid_member");
//            }
            ew.orderByAsc(settingType==2, "rebate","team_actual_lose","team_daily_amount","valid_member");
            ew.orderByAsc(settingType==3, "team_daily_amount","valid_member");
            List<UserBonusSettingEntity> userBonusSettingEntities = userBonusSettingDao.selectList(ew);
            List<UserBonusSettingDTO> dtoList = new ArrayList<>();
            for (UserBonusSettingEntity userBonusSettingEntity : userBonusSettingEntities) {
                UserBonusSettingDTO dto = new UserBonusSettingDTO();
                BeanUtil.copyProperties(userBonusSettingEntity, dto);
                dtoList.add(dto);
            }
            return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<List<UserBonusSettingDTO>> getBonusSettingByIdApi(Integer settingType, Long id) {
            QueryWrapper<UserBonusSettingEntity> ew = new QueryWrapper<>();
            if (id != null) {
                ew.eq("id", id);
            }
            ew.eq("is_del", UserConstant.IS_F);
//        if(settingType!=null)
//            ew.eq("setting_type",settingType);
//    排序：    日工资：返点要求>团队实际亏损>团队日量>有效会员
//            List<String> sortReq = new ArrayList<>();
//            if (settingType == 2) {
//                sortReq.add("rebate");
//                sortReq.add("team_actual_lose");
//                sortReq.add("team_daily_amount");
//                sortReq.add("valid_member");
//            }
//    排序    契约日工资：日量>有效会员
//            if (settingType == 3) {
//                sortReq.add("team_daily_amount");
//                sortReq.add("valid_member");
//            }
            ew.orderByAsc(settingType==2, "rebate", "team_actual_lose", "team_daily_amount", "valid_member");
            ew.orderByAsc(settingType==3, "team_daily_amount","valid_member");
            List<UserBonusSettingEntity> userBonusSettingEntities = userBonusSettingDao.selectList(ew);
            List<UserBonusSettingDTO> dtoList = new ArrayList<>();
            for (UserBonusSettingEntity userBonusSettingEntity : userBonusSettingEntities) {
                UserBonusSettingDTO dto = new UserBonusSettingDTO();
                BeanUtil.copyProperties(userBonusSettingEntity, dto);
                dtoList.add(dto);
            }
            return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getBonusMapApi(Integer settingType, Long mainId) {
            Page page = new Page();
            List<Map<String, Object>> contractList = userBonusSettingDao.queryUserBonusMainMultipleData(mainId, settingType, null, page, null, null, null);
            List<Map<String, Object>> resultList = new ArrayList<>();
            if (contractList != null) {
                for (Map<String, Object> map : contractList) {
                    QueryGameCategoryReq req = new QueryGameCategoryReq();
                    req.setCategoryCode((String) map.get("game_category_id"));
                    ApiResult<com.liying.common.PageInfo<GameCategoryResp>> apiResult = gameCategoryService.queryGameCategory(req);
                    com.liying.common.PageInfo<GameCategoryResp> data = apiResult.getData();
                    if (data == null) {
                        resultList.add(map);
                        continue;
                    }
                    List<GameCategoryResp> content1 = data.getContent();
                    if (content1 == null) {
                        resultList.add(map);
                        continue;
                    }
                    map.put("categoryName", content1.get(0).getCategoryName());
                    resultList.add(map);
                }
            }
            return RPCResult.success(resultList);
    }


    @Override
    public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(Long siteId, String pcode, Integer rebateLevel, int settingType) {
            Map<String, Object> itemMap = new HashMap<>();
            List<GameCategoryResp> cateList = new ArrayList<>();
            QueryGameCategoryReq req = new QueryGameCategoryReq();
//            req.setpCode(pcode);
            req.setPid(0L);
            req.setIsEnable(UserConstant.IS_T);
            ApiResult<PageInfo<GameCategoryResp>> apiResult1 = gameCategoryService.queryGameCategory(req);
            if (RPCResult.checkApiResult(apiResult1)) {
                PageInfo<GameCategoryResp> data = apiResult1.getData();
                if (data.getSize() > 0) {
                    cateList = data.getContent();
                } else {
                    logger.error("查询游戏分类数据为空！");
                    return RPCResult.fail(ErrorCode.DEFAULT_CODE,"查询游戏分类数据为空！");
                }
            }
            List<Map<String, Object>> setList = userBonusSettingDao.queryBonusSetByLevel(rebateLevel, settingType, siteId);
            itemMap.put("cateList", cateList);
//            itemMap.put("lhcRebateList", lhcRebateList);
            itemMap.put("setList", setList);
            logger.info("查询日工资设置相关信息 结果" + itemMap);
            return RPCResult.success(itemMap);
    }


    @Override
    public ApiResult deleteApi(Long siteId, Long id, Long mainId, Long updateByUserId, String ip, String url) {
            logger.info(String.format("修改日工资细单删除状态 son= %s", id));
            if (ObjectUtil.isNull(id)) {
                return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "日工资细单Id不能为空");
            }
            UserBonusSettingEntity model = userBonusSettingDao.selectById(id);
            if (null == model) {
                logger.info("删除日工资细单时候，获取细单信息为空");
                return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "获取日工资细单信息异常");
            }
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setId(updateByUserId);
            sysUserEntity.setIsDel(UserConstant.IS_F);
            SysUserEntity updateBySysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
            if (updateBySysUser == null) {
                return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作管理员用户信息不存在");
            }
            model.setUpdateBy(updateBySysUser.getUserName());
            model.setUpdateTime(new Date());
            model.setIsDel(UserConstant.IS_T);
            logger.info(String.format("删除日工资细单 son= %s", JSONUtil.parse(model).toString()));
            userBonusSettingDao.updateById(model);
            //判断主单下是否还有细单，没有的话要修改主单的is_del为T
            List<UserBonusSettingEntity> newModel = userBonusSettingDao.querySettingByMainId(mainId,UserConstant.IS_ZERO);
            UserBonusMainEntity mainModel = userBonusMainDao.selectById(mainId);
            String cz = "";
            String czType = "";
            String mainLevel = "";
            String content = "";
            String bonusType = "";
            if (mainModel.getSettingType().equals(UserConstant.DAILY)) {
                bonusType = "日工资规则";
                mainLevel = "层级：" + mainModel.getRebateLevel() + "。";
            } else if (mainModel.getSettingType().equals(UserConstant.CONTRACT_DAILY)) {
                bonusType = "契约日工资规则";
            }
            if (ObjectUtil.isNull(newModel)) {
                mainModel.setUpdateBy(updateBySysUser.getUserName());
                mainModel.setUpdateTime(new Date());
                mainModel.setIsDel(UserConstant.IS_T);
                logger.info(String.format("修改代理分红主单 main= %s", JSONUtil.parse(mainModel).toString()));
                userBonusMainDao.updateById(mainModel);
                content = "删除：" + bonusType + "。" + cz + czType + mainLevel;
                logUserInnerService.addUserLog(updateBySysUser.getId(), updateBySysUser.getUserName(), UserConstant.PC, content, UserCfg.DELETE, UserCfg.SITE, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            }
            content = "删除：" + bonusType + "细单。" + cz + czType + mainLevel;
            logUserInnerService.addUserLog(updateBySysUser.getId(), updateBySysUser.getUserName(), UserCfg.PC, content, UserCfg.DELETE, UserCfg.SITE, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.success();
    }

    @Override
    public ApiResult saveApi(UserBonusSettingDTO dto) {
            if (dto == null) {
                return RPCResult.fail();
            }
            UserBonusSettingEntity userBonusSettingEntity = new UserBonusSettingEntity();
            BeanUtil.copyProperties(dto, userBonusSettingEntity);
            Integer insert = userBonusSettingDao.insert(userBonusSettingEntity);
            return insert > 0 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult updateApi(UserBonusSettingDTO dto) {
            Assert.isNull(dto.getId(), "主键不能为空");
            UserBonusSettingEntity userBonusSettingEntity = new UserBonusSettingEntity();
            BeanUtil.copyProperties(dto, userBonusSettingEntity);
            Integer integer = userBonusSettingDao.updateById(userBonusSettingEntity);
            return integer > 0 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<List<Map<String, Object>>> queryUserBonusSettingApi(Long mainId) {
            List<Map<String, Object>> resultList = userBonusSettingDao.queryUserBonusSettingApi(mainId);
            return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> querySettingByQy(String teamDailyAmount, Integer validMember, Integer bonus, Integer daysPer, Long mainId, Long id, Integer bonusMode) {
            List<Map<String, Object>> settingList = userBonusSettingDao.querySettingByQy(teamDailyAmount, validMember, bonus, daysPer, mainId, id, bonusMode);
            return RPCResult.success(settingList);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> querySettingByQyApi(String teamDailyAmount, Long mainId, Long id) {
            List<Map<String, Object>> settingList = userBonusSettingDao.querySettingByQyApi(teamDailyAmount, mainId, id);
            return RPCResult.success(settingList);
    }

    @Override
    public ApiResult updateUserBonusSetting(UserBonusMainSettingDTO dto) {
        ApiResult checkResult = checkParam(dto);
        if(!Results.isSuccess(checkResult)){
            logger.warn("修改日工资设置:参数校验失败:"+checkResult.getMessage());
            return checkResult;
        }

        StringBuffer sb = new StringBuffer();
        UserBonusMainEntity entity = new UserBonusMainEntity();
            if(StringUtils.isEmpty(dto.id)){
                //新增
                entity = addUserBonusSetting(dto);
                sb.append("新增");
                UserEntity user = userDao.findById(entity.getToUserId());
                if(BonusConstant.BonusSettingType.DAILY == dto.getSettingType()){
                    sb.append(String.format("日工资 游戏大类:{%s} 层级:{%s}",dto.getGameCategoryId(),dto.getRebateLevel()));
                }else if(BonusConstant.BonusSettingType.CONTRACT_DAILY == dto.getSettingType()){
                    sb.append(String.format("契约日工资 游戏大类:{%s} 被契约用户:{%s}",dto.getGameCategoryId(),user.getUserName()));
                }
                logUserInnerService.addUserLog(dto.getSysUserId(), dto.getCreateBy(), UserConstant.PC, sb.toString(), UserCfg.ADD, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
                sb.append(String.format(" 主单id={%s}",entity.getId()));
                logger.info(sb.toString());
            }else{
                //修改
                UserBonusMainEntity oldBonusMain = userBonusMainDao.selectById(dto.getId());
                if(oldBonusMain == null || oldBonusMain.getIsDel() == UserConstant.IS_T){
                    return Results.fail("该设置已被删除");
                }
                //备份原有设置
                UserBonusMainEntity mainEntity = new UserBonusMainEntity();
                mainEntity.setId(oldBonusMain.getId());
                mainEntity.setIsDel(UserConstant.IS_T);
                mainEntity.setUpdateBy(dto.getCreateBy());
                mainEntity.setUpdateTime(new Date());
                userBonusMainDao.updateById(mainEntity);
                QueryWrapper<UserBonusSettingEntity> ew = new QueryWrapper();
                ew.eq("main_id",oldBonusMain.getId());
                UserBonusSettingEntity settingEntity = new UserBonusSettingEntity();
                settingEntity.setIsDel(UserConstant.IS_T);
                settingEntity.setUpdateBy(dto.getCreateBy());
                settingEntity.setUpdateTime(new Date());
                userBonusSettingDao.update(settingEntity,ew);
                //如果周期被修改
                if(dto.getBonusCycle() != oldBonusMain.getBonusCycle()){
                    dto.setSettleTime(new Date());
                }
                dto.setUserId(oldBonusMain.getUserId());
                dto.setToUserId(oldBonusMain.getToUserId());
                entity = addUserBonusSetting(dto);
                sb.append("修改");
                UserEntity user = userDao.findById(oldBonusMain.getToUserId());
                if(BonusConstant.BonusSettingType.DAILY == dto.getSettingType()){
                    sb.append(String.format("日工资 游戏大类:{%s} 层级:{%s}",dto.getGameCategoryId(),dto.getRebateLevel()));
                }else if(BonusConstant.BonusSettingType.CONTRACT_DAILY == dto.getSettingType()){
                    sb.append(String.format("契约日工资 游戏大类:{%s} 被契约用户:{%s}",dto.getGameCategoryId(),user.getUserName()));
                }
                logUserInnerService.addUserLog(dto.getSysUserId(), dto.getCreateBy(), UserConstant.PC, sb.toString(), UserCfg.UPDATE, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
                sb.append(String.format(" 原有主单id={%s} 修改后主单id={%s} ",dto.getId(),entity.getId()));
                logger.info(sb.toString());
            }
            return Results.success(entity);
    }

    /**
     * 新增日工资设置 包含主单
     * @param dto
     */
    private UserBonusMainEntity addUserBonusSetting(UserBonusMainSettingDTO dto) {
        UserBonusMainEntity entity = new UserBonusMainEntity();
        BeanUtil.copyProperties(dto,entity);
        entity.setId(IdWorker.getId());
        entity.setCreateTime(new Date());
        entity.setCreateBy(dto.getCreateBy());
        entity.setIsDel(UserConstant.IS_F);
        entity.setSiteCode(dto.getSiteCode());
        if(ArrayUtil.isNotEmpty(dto.getGameList())){
            entity.setPlayType(JSONUtil.toJsonStr(dto.getGameList()));
        }
        if(BonusConstant.BonusSettingType.DAILY == dto.getSettingType()){
            entity.setSignStatus(BonusConstant.SignStatus.AGREE);
            entity.setSignTime(new Date());
        }else if(BonusConstant.BonusSettingType.CONTRACT_DAILY == dto.getSettingType()){
            entity.setSignStatus(BonusConstant.SignStatus.NOT_SIGN);
        }
        if(dto.getSettleTime() != null){
            entity.setSettleTime(new Date());
        }
//
//        Map<String,String> time = new HashMap<>();
//        Map<String,String> time1 = new HashMap<>();
        List<Map<String,String>> time = new ArrayList<>();

        //新增细单
        List<UserBonusSettingEntity> settingList = new ArrayList<>();
        List<UserBonusSettingDTO> settingDTOList = new ArrayList<>();
        for(int i=0 ; i<dto.getValidMember().length;i++){
            UserBonusSettingEntity settingEntity = new UserBonusSettingEntity();
            settingEntity.setBonusMode(Integer.parseInt(dto.getBonusMode()[i]));
            settingEntity.setMainId(entity.getId());
            settingEntity.setValidMember(Integer.parseInt(dto.getValidMember()[i]));
            settingEntity.setCreateTime(new Date());
            settingEntity.setCreateBy(dto.getCreateBy());
            settingEntity.setIsDel(UserConstant.IS_F);
            settingEntity.setTeamDailyAmount(Long.parseLong(dto.getTeamDailyAmount()[i])*100);

            if(BonusConstant.BonusSettingType.DAILY == dto.getSettingType()){
                if(CollectionUtil.isNotEmpty(time)){
                    for (Map<String, String> map : time) {
                        if(MapUtil.isNotEmpty(map)){
                            if(dto.getValidMember()[i].equals(map.get("valid_member")) && dto.getTeamActualLose()[i].equals(map.get("actual_winLose")) && dto.getTeamDailyAmount()[i].equals(map.get("daily_amount"))){
                                throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "已存在和第" + (i + 1) + "条一样的细单信息!");
                            }
                        }
                    }
                }

                Map<String,String> checkMap = new HashMap<>();
                checkMap.put("valid_member",dto.getValidMember()[i]);
                checkMap.put("daily_amount",dto.getTeamDailyAmount()[i]);
                checkMap.put("actual_winLose",dto.getTeamActualLose()[i]);
                time.add(checkMap);

                settingEntity.setRebate(Long.parseLong(dto.getRebate()[i])*100);
                settingEntity.setTeamActualLose(Long.parseLong(dto.getTeamActualLose()[i])*100);


                if("0".equals(dto.getBonusMode()[i])){
                    Double daysPer = Double.parseDouble(dto.getDaysPer()[i]) * 100;
                    settingEntity.setDaysPer(daysPer.intValue());
                }else if("1".equals(dto.getBonusMode()[i])){
                    Double ykPer = Double.parseDouble(dto.getYkPer()[i]) * 100;
                    Double bouns = Double.parseDouble(dto.getBonus()[i]) * 100;
                    settingEntity.setYkPer(ykPer.intValue());
                    settingEntity.setBonus(bouns.intValue());
                }

            }else if(BonusConstant.BonusSettingType.CONTRACT_DAILY == dto.getSettingType()){
                if(CollectionUtil.isNotEmpty(time)){
                    for (Map<String, String> map : time) {
                        if(MapUtil.isNotEmpty(map)){
                            if(dto.getValidMember()[i].equals(map.get("valid_member")) && dto.getTeamDailyAmount()[i].equals(map.get("daily_amount"))){
                                throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "已存在和第" + (i + 1) + "条一样的细单信息!");
                            }
                        }
                    }
                }

                Map<String,String> checkMap = new HashMap<>();
                checkMap.put("valid_member",dto.getValidMember()[i]);
                checkMap.put("daily_amount",dto.getTeamDailyAmount()[i]);
                time.add(checkMap);

                if("0".equals(dto.getBonusMode()[i])){
                    Double daysPer = Double.parseDouble(dto.getDaysPer()[i]) * 100;
                    settingEntity.setDaysPer(daysPer.intValue());
                }else if("1".equals(dto.getBonusMode()[i])){
                    Double bouns = Double.parseDouble(dto.getBonus()[i]) * 100;
                    settingEntity.setBonus(bouns.intValue());
                }
            }
            settingList.add(settingEntity);
            UserBonusSettingDTO settingDTO = new UserBonusSettingDTO();
            BeanUtil.copyProperties(settingEntity,settingDTO);
            settingDTOList.add(settingDTO);
        }
        userBonusMainDao.insert(entity);
        userBonusSettingDao.batchSaveUserBonusSetting(settingList);

//        //把最新的规则先存起来，结算的时候用
//        UserBonusMainDTO mainDTO = new UserBonusMainDTO();
//        BeanUtil.copyProperties(entity, mainDTO);
//        mainDTO.setBonusSettingList(settingDTOList);
//        ValueOperations<String, String> ops = template.opsForValue();
//        ops.set(RedisKey.BONUS_SETTINGTYPE + dto.getSettingType() + ":" + mainDTO.getId(), JSONUtil.toJsonStr(mainDTO));
//        logger.info(String.format("bonusSetting缓存对象 = %s", RedisKey.BONUS_SETTINGTYPE + dto.getSettingType() + ":" + mainDTO.getId()));

        return entity;
    }


    /**
     * 参数校验
     * @param dto
     * @return
     */
    private ApiResult checkParam(UserBonusMainSettingDTO dto) {

        Pattern pat = Pattern.compile("[0-9]+");
        Pattern pat2 = Pattern.compile("^100$|^100.0$|^(\\d|[1-9]\\d)(\\.[0-9]{1})*$");
        Pattern pat1 = Pattern.compile("^100$|^100.0$|^100.00$|^(\\d|[1-9]\\d)(\\.[0-9]{1})*$|^(\\d|[1-9]\\d)(\\.[0-9]{2})*$");

        if(null == dto){
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"入参为空");
        }
        //工资周期
        Integer bonusCycle = dto.getBonusCycle();
        if(bonusCycle == null ){
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"工资周期为空");
        }

        //奖金模式
        String[] bonusMode = dto.getBonusMode();
        if(ArrayUtil.isEmpty(bonusMode) ){
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"奖金模式为空");
        }

        //设置类型
        Integer settingType = dto.getSettingType();
        if(settingType == null ){
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"设置类型为空");
        }

//        //游戏类型
//        if(StringUtils.isEmpty(dto.getGameCategoryId())){
//            return Results.fail("游戏大类为空");
//        }

        //有效会员
        String[] validMember = dto.getValidMember();
        if(ArrayUtil.isEmpty(validMember)){
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"有效会员数量为空");
        }else{

            for(int i = 0;i < validMember.length;i++){
                if (validMember[i].length() <= 0 || !pat.matcher(validMember[i]).matches()) {
                    return Results.fail("有效会员应为正整数");
                } else if (validMember[i].length() > 12) {
                    return Results.fail("有效会员不能大于12位数");
                }
                //团队日量
                String[] dailyAmount = dto.getTeamDailyAmount();
                if(ArrayUtil.isEmpty(dailyAmount)){
                    return Results.fail("团队日量不能为空");
                }
                if (dailyAmount[i].length() <= 0 || !pat.matcher(dailyAmount[i]).matches()) {
                    return Results.fail("团队日量应为正整数");
                } else if (dailyAmount[i].length() > 12) {
                    return Results.fail("团队日量不能大于12位数");
                }

                if(BonusConstant.BonusSettingType.DAILY == settingType){

                    if(null == dto.getRebateLevel()){
                        return Results.fail("代理层级不能为空");
                    }
                    //日工资
                    //返点
//                    String[] rebate = dto.getRebate();
//                    if (ArrayUtil.isEmpty(rebate)) {
//                        return Results.fail("返点要求不能为空");
//                    }
//                    if (rebate[i].length() <= 0 || !pat2.matcher(rebate[i]).matches()) {
//                        return Results.fail("返点要求应填0-100，可带1位小数");
//                    } else if (rebate[i].length() > 12) {
//                        return Results.fail("返点要求不能大于12位数");
//                    }
                    int mode = NumberUtils.toInt(bonusMode[i]);
                    if (mode == 0) {
                        if("0".equals(dto.getValidMember()[i])&&"0".equals(dto.getTeamDailyAmount()[i])&&"0".equals(dto.getTeamActualLose()[i])&&"0".equals(dto.getDaysPer()[i])){
                            logger.info("都为0 为无效条件");
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                        }
                        //日量比例
                        String[] daysPer = dto.getDaysPer();
                        if (ArrayUtil.isEmpty(daysPer)) {
                            return Results.fail("日量比例为空");
                        }
                        if (daysPer[i].length() <= 0 || !pat1.matcher(daysPer[i]).matches()) {
                            return Results.fail("日量比例应填0-100，可带2位小数");
                        }
                    } else if (mode == 1) {
                        if("0".equals(dto.getValidMember()[i])&&"0".equals(dto.getTeamDailyAmount()[i])&&"0".equals(dto.getTeamActualLose()[i])&&"0".equals(dto.getYkPer()[i])&&"0".equals(dto.getBonus()[i])){
                            logger.info("都为0 为无效条件");
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                        }
                        String[] bonus = dto.getBonus();
                        String[] ykPer = dto.getYkPer();
                        if (ArrayUtil.isEmpty(bonus)) {
                            return Results.fail("奖金金额为空");
                        }
                        if (ArrayUtil.isEmpty(ykPer)) {
                            return Results.fail("盈亏比例为空");
                        }
                        if (bonus[i].length() <= 0 || !pat.matcher(bonus[i]).matches()) {
                            return Results.fail("上限金额应为正整数");
                        } else if (bonus[i].length() > 12) {
                            return Results.fail("上限金额不能大于12位数");
                        }
                        if (ykPer[i].length() <= 0 || !pat1.matcher(ykPer[i]).matches()) {
                            return Results.fail("盈亏比例应填0-100，可带2位小数");
                        }
                    }
                    //团队实际盈亏
                    String[] actualLose = dto.getTeamActualLose();
                    if(ArrayUtil.isEmpty(actualLose)){
                        return Results.fail("团队实际盈亏为空");
                    }
                    if (actualLose[i].length() <= 0 || !pat.matcher(actualLose[i]).matches()) {
                        return Results.fail("团队实际亏损要求应为正整数");
                    } else if (actualLose[i].length() > 12) {
                        return Results.fail("团队实际亏损不能大于12位数");
                    }
                }else if(BonusConstant.BonusSettingType.CONTRACT_DAILY == settingType){
                    //契约日工资
                    int mode = NumberUtils.toInt(bonusMode[i]);
                    if (mode == 0) {
                        if("0".equals(dto.getValidMember()[i])&&"0".equals(dto.getTeamDailyAmount()[i])&&"0".equals(dto.getTeamActualLose()[i])&&"0".equals(dto.getDaysPer()[i])){
                            logger.info("都为0 为无效条件");
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                        }
                        //日量比例
                        String[] daysPer = dto.getDaysPer();
                        if(ArrayUtil.isEmpty(daysPer)){
                            return Results.fail("奖金比例为空");
                        }
                        if (daysPer[i].length() <= 0 || !pat1.matcher(daysPer[i]).matches()) {
                            return Results.fail("奖金比例应填0-100，可带2位小数");
                        }
                    } else if (mode == 1) {
                        if("0".equals(dto.getValidMember()[i])&&"0".equals(dto.getTeamDailyAmount()[i])&&"0".equals(dto.getTeamActualLose()[i])&&"0".equals(dto.getBonus()[i])){
                            logger.info("都为0 为无效条件");
                            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "条件不能都为0");
                        }
                        String[] bonus = dto.getBonus();
                        if(ArrayUtil.isEmpty(bonus)){
                            return Results.fail("奖金金额为空");
                        }
                        if (bonus[i].length() <= 0 || !pat.matcher(bonus[i]).matches()) {
                            return Results.fail("上限金额应为正整数");
                        } else if (bonus[i].length() > 12) {
                            return Results.fail("上限金额不能大于12位数");
                        }
                    }
                }else{
                    return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(),"设置类型错误");
                }
            }
        }
        return Results.success();
    }
}
