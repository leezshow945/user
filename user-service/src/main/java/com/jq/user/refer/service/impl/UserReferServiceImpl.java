package com.jq.user.refer.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.domain.service.DomainService;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.service.UserRebateInnerService;
import com.jq.user.customer.support.CheckUtil;
import com.jq.user.exception.UserException;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.refer.dao.UserReferDao;
import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.entity.UserReferEntity;
import com.jq.user.refer.service.UserReferInnerService;
import com.jq.user.refer.support.Utils;
import com.jq.user.support.PageUtil;
import com.jq.user.support.RedisUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈推广链接〉
 *
 * @author Json
 * @create 2018//25
 */
@Service
@Transactional
public class UserReferServiceImpl implements UserReferInnerService {


    private final static Logger logger = (Logger) LoggerFactory.getLogger(UserReferServiceImpl.class);


    @Resource
    private UserReferDao userReferDao;
    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Autowired
    private UserDao userDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SiteService siteService;
    @Resource
    private KeyValueService keyValueService;
    @Resource
    private DomainService domainService;

    @Override
    public UserReferEntity findBySiteIdAndReferCode(Long siteId, String referCode) {
        if (siteId == null || StringUtils.isEmpty(referCode)) {
            return null;
        }
        UserReferEntity entity = new UserReferEntity();
        entity.setSiteId(siteId);
        entity.setReferCode(referCode);
        entity.setIsDel(UserConstant.IS_F);
        return userReferDao.selectOne(new QueryWrapper<>(entity));
    }

    @Override
    public UserReferEntity findByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        UserReferEntity entity = new UserReferEntity();
        entity.setUserId(userId);
        return userReferDao.selectOne(new QueryWrapper<>(entity));
    }


    @Override
    public ApiResult<PageInfo<UserReferDTO>> getListByConditionApi(UserReferDTO dto) {
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        Map<String, Object> map = BeanUtil.beanToMap(dto);
        List<UserReferDTO> list = userReferDao.findByCondition(map, page);
        PageInfo<UserReferDTO> listPageInfo = new PageInfo<>(list, (int) page.getCurrent(), (int) page.getSize(), page.getTotal());
        return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult saveApi(UserReferDTO dto) {
        //分布式锁操作推广链接
        String cacheKey = RedisKey.Lock.LOCK_USER_SIGN + dto.getUserId();
        RedisUtil redisUtil = new RedisUtil(redisTemplate, cacheKey);
        Assert.isNull(dto.getUserId(), "系统异常");
        if (0 == dto.getLinkType()) {
            Assert.isNull(dto.getDomainUrl(), "本站地址不允许为空");
        } else {
            //替换成自定义地址
            dto.setDomainUrl(dto.getDomainUrl1());
            if (StringUtils.isEmpty(dto.getDomainUrl()))
                Assert.isEmpty(dto.getDomainUrl(), "自定义地址不允许为空");
            if (!Utils.isUrl(dto.getDomainUrl())) {
                logger.info(String.format("自定义地址域名不符合规则！{%s}", dto.getDomainUrl()));
                throw new UserException(UserCodeEnum.DOMAINURL_IS_ILLEGAL.getCode());
            }

            UserReferDTO userReferDTO = new UserReferDTO();
            userReferDTO.setDomainUrl(dto.getDomainUrl());
            QueryWrapper<UserReferEntity> ew = new QueryWrapper<>();
            ew.eq("domain_url", dto.getDomainUrl());
            ew.eq("link_type", dto.getLinkType());
            ew.eq("is_del", UserConstant.IS_F);
            List<UserReferEntity> userReferEntities = userReferDao.selectList(ew);
            if (CollectionUtil.isNotEmpty(userReferEntities)) {
                logger.info(String.format("自定义地址域名重复！{%s}", dto.getDomainUrl()));
                throw new UserException(UserCodeEnum.DOMAINURL_IS_REPEAT.getCode());
            }
        }
        UserRebateEntity rebate = userRebateInnerService.getRebate(dto.getUserId());
        if (rebate == null) {
            logger.info(String.format("代理线已被删除！{%s}", dto.getUserId()));
            throw new UserException(UserCodeEnum.USERREBATE_IS_DEL.getCode());
        }

        //校验推广码格式
        //字母和数字
        String reg1 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6}$";
        //纯数字
        String reg2 = "^[0-9]{6}$";
        //纯字母
        String reg3 = "^[a-zA-Z]{6}";
        if (StringUtils.isNotBlank(dto.getReferCode())) {
            String referCodeMatch = dto.getReferCode();
            if (!(referCodeMatch.matches(reg1) || referCodeMatch.matches(reg2) || referCodeMatch.matches(reg3))) {
                logger.info(String.format("推广码不符合格式！dto.getDomainUrl():{%s}", dto.getReferCode()));
                throw new UserException(UserCodeEnum.REFER_CODE_VALID_FALSE.getCode());
            }
            QueryWrapper<UserReferEntity> ew = new QueryWrapper<>();
            ew.eq("refer_code", dto.getReferCode());
            ew.eq("site_id", dto.getSiteId());
            ew.eq("is_del", UserConstant.IS_F);
            List<UserReferEntity> userReferEntities = userReferDao.selectList(ew);
            if (CollectionUtil.isNotEmpty(userReferEntities)) {
                logger.info(String.format("推广码已存在 {%s}", dto.getReferCode()));
                throw new UserException(UserCodeEnum.REFER_CODE_IS_EXIST.getCode());
            }
        }
        boolean lock = false;
        try {
            lock = redisUtil.lock();
        } catch (Exception e) {
            logger.warn(String.format("用户推广链接数据被锁住 {%s}", JSONUtil.toJsonStr(dto)));
            throw new UserException(ErrorCode.DEFAULT_CODE, ErrorCode.DEFAULT_MSG);
        }
        if (!lock) {
            return RPCResult.fail(ErrorCode.DEFAULT_CODE, "操作频繁，请稍后再试");
        }
        //返点数据校验
        if (null != dto.getDpcRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getDpcRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setDpcRebate(dto.getDpcRebate() * 10 * 10);
            if (dto.getDpcRebate() > rebate.getDpcRebate())
                throw new UserException(UserCodeEnum.DPC_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getFlcRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getFlcRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setFlcRebate(dto.getFlcRebate() * 10 * 10);
            if (dto.getFlcRebate() > rebate.getFlcRebate())
                throw new UserException(UserCodeEnum.FLC_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getGpcRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getGpcRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setGpcRebate(dto.getGpcRebate() * 10 * 10);
            if (dto.getGpcRebate() > rebate.getGpcRebate())
                throw new UserException(UserCodeEnum.GPC_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getQtRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getQtRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setQtRebate(dto.getQtRebate() * 10 * 10);
            if (dto.getQtRebate() > rebate.getQtRebate())
                throw new UserException(UserCodeEnum.QT_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getTycpRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getTycpRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setTycpRebate(dto.getTycpRebate() * 10 * 10);
            if (dto.getTycpRebate() > rebate.getTycpRebate())
                throw new UserException(UserCodeEnum.TYCP_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getTyRebate()) {
            if (!CheckUtil.isOneDecimal(dto.getTyRebate())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setTyRebate(dto.getTyRebate() * 10 * 10);
            if (dto.getTyRebate() > rebate.getTyRebate())
                throw new UserException(UserCodeEnum.TY_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getLhcRebate0()) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate0())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setLhcRebate0(dto.getLhcRebate0() * 10 * 10);
            if (dto.getLhcRebate0() > rebate.getLhcRebate0())
                throw new UserException(UserCodeEnum.LHC0_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getLhcRebate1()) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate1())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setLhcRebate1(dto.getLhcRebate1() * 10 * 10);
            if (dto.getLhcRebate1() > rebate.getLhcRebate1())
                throw new UserException(UserCodeEnum.LHC1_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getLhcRebate2()) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate2())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setLhcRebate2(dto.getLhcRebate2() * 10 * 10);
            if (dto.getLhcRebate2() > rebate.getLhcRebate2())
                throw new UserException(UserCodeEnum.LHC2_REBATE_OVER_TARGET.getCode());
        }

        if (null != dto.getLhcRebate3()) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate3())) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            dto.setLhcRebate3(dto.getLhcRebate3() * 10 * 10);
            if (dto.getLhcRebate3() > rebate.getLhcRebate3())
                throw new UserException(UserCodeEnum.LHC3_REBATE_OVER_TARGET.getCode());
        }

        //默认启用
        dto.setIsEnable(UserConstant.IS_T);
        UserReferEntity entity = new UserReferEntity();
        BeanUtil.copyProperties(dto, entity);
        entity.setCreateTime(new Date());
        //生成6位随机码
        while (StringUtils.isBlank(entity.getReferCode())) {
            String newCode = RandomUtil.randomString(6);
            QueryWrapper ew = new QueryWrapper<UserReferEntity>();
            ew.eq("refer_code", newCode);
            ew.eq("is_del", UserConstant.IS_F);
            List list = userReferDao.selectList(ew);
            if (CollectionUtil.isEmpty(list))
                entity.setReferCode(newCode);
        }
        //根据站点id 查询站点信息
        ApiResult<SiteDTO> result = siteService.findSiteDTOById(entity.getSiteId());
        SiteDTO siteDTO = result.getData();
        if (siteDTO == null)
            throw new UserException(UserCodeEnum.SITE_NOT_EXIST.getCode());
        entity.setReferUrl(entity.getDomainUrl() + siteDTO.getLinkIdent() + entity.getReferCode());
        boolean insert = userReferDao.insert(entity) > 0;
        if (insert) {
            String content = "新增：推广链接，推广码" + entity.getReferCode() + "。链接：" + entity.getReferUrl();
            logUserInnerService.addUserLog(null, dto.getCreateBy(), UserConstant.PC, content, UserCfg.ADD, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
        }

        return insert ? RPCResult.success() : RPCResult.fail();

    }

    @Override
    public ApiResult<UserReferDTO> getByIdApi(Long id) {
        Assert.isNull(id, "主键不能为空");
        logger.info(String.format("根据主键Id查询代理账户返点信息，loginName:{%s}", id));
        UserReferDTO dto = userReferDao.selectByReferId(id);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult updateApi(UserReferDTO dto) {
        Assert.isNull(dto.getId(), "主键不能为空");
        UserReferEntity entity = userReferDao.selectByIdAndStatus(dto.getId(), UserConstant.IS_F);
        Assert.isNull(entity, "该推广链接已被删除");
        if (0 == dto.getLinkType()) {
            Assert.isNull(dto.getDomainUrl(), "本站地址不允许为空");
        } else {
            if (!entity.getDomainUrl().equals(dto.getDomainUrl1())) {
                //替换成自定义地址
                dto.setDomainUrl(dto.getDomainUrl1());
                if (StringUtils.isEmpty(dto.getDomainUrl()))
                    Assert.isNull(dto.getDomainUrl(), "本站地址不允许为空");
                if (!Utils.isUrl(dto.getDomainUrl())) {
                    logger.info(String.format("自定义地址域名不符合规则！dto.getDomainUrl():{%s}", dto.getDomainUrl()));
                    throw new UserException(UserCodeEnum.DOMAINURL_IS_ILLEGAL.getCode());
                }


                UserReferDTO userReferDTO = new UserReferDTO();
                userReferDTO.setDomainUrl(dto.getDomainUrl());
                QueryWrapper<UserReferEntity> ew = new QueryWrapper<>();
                ew.eq("domain_url", dto.getDomainUrl());
                ew.eq("link_type", dto.getLinkType());
                List<UserReferEntity> userReferEntities = userReferDao.selectList(ew);
                if (CollectionUtil.isNotEmpty(userReferEntities)) {
                    throw new UserException(UserCodeEnum.DOMAINURL_IS_REPEAT.getCode());
                }
            }

        }
        UserRebateEntity rebate = userRebateInnerService.getRebate(entity.getUserId());
        Assert.isNull(rebate, "目标代理已被删除");

        if (dto.getDpcRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getDpcRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setDpcRebate(dto.getDpcRebate() * 10 * 10);
            if (dto.getDpcRebate() > rebate.getDpcRebate())
                throw new UserException(UserCodeEnum.DPC_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getFlcRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getFlcRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setFlcRebate(dto.getFlcRebate() * 10 * 10);
            if (dto.getFlcRebate() > rebate.getFlcRebate())
                throw new UserException(UserCodeEnum.FLC_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getGpcRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getGpcRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setGpcRebate(dto.getGpcRebate() * 10 * 10);
            if (dto.getGpcRebate() > rebate.getGpcRebate())
                throw new UserException(UserCodeEnum.GPC_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getQtRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getQtRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setQtRebate(dto.getQtRebate() * 10 * 10);
            if (dto.getQtRebate() > rebate.getQtRebate())
                throw new UserException(UserCodeEnum.QT_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getTycpRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getTycpRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setTycpRebate(dto.getTycpRebate() * 10 * 10);
            if (dto.getTycpRebate() > rebate.getTycpRebate())
                throw new UserException(UserCodeEnum.TYCP_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getTyRebate() != null) {
            if (!CheckUtil.isOneDecimal(dto.getTyRebate()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setTyRebate(dto.getTyRebate() * 10 * 10);
            if (dto.getTyRebate() > rebate.getTyRebate())
                throw new UserException(UserCodeEnum.TY_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getLhcRebate0() != null) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate0()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setLhcRebate0(dto.getLhcRebate0() * 10 * 10);
            if (dto.getLhcRebate0() > rebate.getLhcRebate0())
                throw new UserException(UserCodeEnum.LHC0_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getLhcRebate1() != null) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate1()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setLhcRebate1(dto.getLhcRebate1() * 10 * 10);
            if (dto.getLhcRebate1() > rebate.getLhcRebate1())
                throw new UserException(UserCodeEnum.LHC1_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getLhcRebate2() != null) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate2()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setLhcRebate2(dto.getLhcRebate2() * 10 * 10);
            if (dto.getLhcRebate2() > rebate.getLhcRebate2())
                throw new UserException(UserCodeEnum.LHC2_REBATE_OVER_TARGET.getCode());
        }
        if (dto.getLhcRebate3() != null) {
            if (!CheckUtil.isOneDecimal(dto.getLhcRebate3()))
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            dto.setLhcRebate3(dto.getLhcRebate3() * 10 * 10);
            if (dto.getLhcRebate3() > rebate.getLhcRebate3())
                throw new UserException(UserCodeEnum.LHC3_REBATE_OVER_TARGET.getCode());
        }

        UserReferEntity refer = new UserReferEntity();
        BeanUtil.copyProperties(dto, refer);
        refer.setUpdateTime(new Date());
//            if (!entity.getDomainUrl().equals(dto.getDomainUrl1())) {
        //根据站点id 查询站点信息
        ApiResult<SiteDTO> result = siteService.findSiteDTOById(refer.getSiteId());
        SiteDTO siteDTO = result.getData();
        if (siteDTO != null)
            refer.setReferUrl(refer.getDomainUrl() + siteDTO.getLinkIdent() + entity.getReferCode());
//            }

        refer.setUpdateBy(refer.getUpdateBy());

        boolean update = userReferDao.updateById(refer) > 0;
        if (update) {
            String content = "修改：推广链接 " + dto.getReferCode();
            logUserInnerService.addUserLog(null, dto.getUpdateBy(), UserConstant.PC, content, UserCfg.UPDATE, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
        }

        return update ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult updateStatusApi(UserReferDTO dto) {
        Assert.isNull(dto.getId(), "主键不能为空");
        Assert.isNull(dto.getIsEnable(), "待修改状态不能为空");
        Assert.isEmpty(dto.getUpdateBy(), "操作人不能为空");
        ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(dto.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOById)) {
            throw new UserException(siteDTOById.getCode());
        }
        SiteDTO data = siteDTOById.getData();
        if (data != null) {
            if (dto.getId().longValue() == data.getDefaultProxyId().longValue())
                throw new UserException(UserCodeEnum.REFER_CODE_IS_DEFAULT_PROXY.getCode());
        }
        UserReferEntity entity = new UserReferEntity();
        entity.setId(dto.getId());
        entity.setIsEnable(dto.getIsEnable());
        entity.setUpdateBy(dto.getUpdateBy());
        entity.setUpdateTime(new Date());
        boolean update = userReferDao.updateById(entity) > 0;
        if (update) {
            String content = "";
            if (dto.getIsEnable() == 0) {
                content = "更新: 停用推广链接 " + dto.getReferCode();
            } else {
                content = "更新: 启用推广链接 " + dto.getReferCode();
            }
            logUserInnerService.addUserLog(null, dto.getUpdateBy(), UserConstant.PC, content, UserCfg.UPDATE, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
        }
        return update ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult deleteApi(UserReferDTO dto) {
        Assert.isNull(dto.getId(), "主键不能为空");
        Assert.isNull(dto.getUpdateBy(), "操作人不能为空");
        ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(dto.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOById)) {
            throw new UserException(siteDTOById.getCode());
        }
        SiteDTO data = siteDTOById.getData();
        if (data != null) {
            if (dto.getId().longValue() == data.getDefaultProxyId().longValue())
                throw new UserException(UserCodeEnum.REFER_CODE_IS_DEFAULT_PROXY.getCode());
        }
        UserReferEntity entity = new UserReferEntity();
        entity.setId(dto.getId());
        entity.setIsDel(UserConstant.IS_T);
        entity.setUpdateBy(dto.getUpdateBy());
        entity.setUpdateTime(new Date());
        boolean delete = userReferDao.updateById(entity) > 0;
        if (delete) {
            String content = "删除：推广链接 " + dto.getReferCode();
            logUserInnerService.addUserLog(null, dto.getUpdateBy(), UserConstant.PC, content, UserCfg.DELETE, dto.getOperatorType(), dto.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), null);
        }
        return delete ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<List<UserReferDTO>> getByDomainUrlApi(Long siteId, Integer linkType, String domainUrl) {
        Assert.isEmpty(domainUrl, "域名不能为空!");
        QueryWrapper<UserReferEntity> ew = new QueryWrapper<>();
        ew.eq("domain_url", domainUrl);
        ew.eq("link_type", linkType);
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("is_enable", UserConstant.IS_T);
        List<UserReferEntity> userReferEntities = userReferDao.selectList(ew);
        List<UserReferDTO> referList = new ArrayList<>();
        for (UserReferEntity userReferEntity : userReferEntities) {
            UserReferDTO dto = new UserReferDTO();
            BeanUtil.copyProperties(userReferEntity, dto);
            referList.add(dto);
        }
        return RPCResult.success(referList);
    }

    @Override
    public ApiResult<Map<Long, String>> getUserNameById(List<Long> idList) {
        if (CollectionUtil.isEmpty(idList))
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(), "参数为空");
        List<Map<String, Object>> userNameById = userReferDao.getUserNameById(idList);
        Map<Long, String> map = new HashMap<>();
        for (Map<String, Object> longStringMap : userNameById) {
            map.put((Long) longStringMap.get("id"), (String) longStringMap.get("userName"));
        }
        return RPCResult.success(map);
    }

    @Override
    public boolean updateReferUseCount(Long id) {
        UserReferEntity userReferEntity = userReferDao.selectById(id);
        if (null == userReferEntity) {
            return false;
        }
        userReferEntity.setUseCount(userReferEntity.getUseCount() + 1);
        return userReferDao.updateById(userReferEntity) == 1;
    }
}
