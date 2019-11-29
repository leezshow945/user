package com.jq.user.oldbonus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.framework.core.exception.Assert;
import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.jq.user.bonus.service.UserBonusSonService;
import com.jq.user.constant.UserConstant;
import com.jq.user.oldbonus.dao.UserBonusSonDao;
import com.jq.user.oldbonus.entity.UserBonusSonEntity;
import com.jq.user.oldbonus.service.UserBonusSonInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/19
 */
@Service
@Transactional
public class UserBonusSonServiceImpl implements UserBonusSonInnerService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(UserBonusSonServiceImpl.class);


    @Autowired
    private UserBonusSonDao userBonusSonDao;

    @Override
    public ApiResult<List<UserBonusSonDTO>> queryUserBonusSonApi(Long mainId, String rebate, String amount, String sort) {
            Assert.isNull(mainId,"主单主键不能为空");
            List<UserBonusSonDTO> list=new ArrayList<>();
            List<UserBonusSonEntity> userBonusSonEntities = userBonusSonDao.queryUserBonusSon(mainId, rebate, amount, sort);
            if(CollectionUtil.isNotEmpty(userBonusSonEntities)){
                for (UserBonusSonEntity userBonusSonEntity : userBonusSonEntities) {
                    UserBonusSonDTO dto=new UserBonusSonDTO();
                    BeanUtil.copyProperties(userBonusSonEntity,dto);
                    list.add(dto);
                }
            }
            return RPCResult.success(list);
    }

    @Override
    public ApiResult updateApi(UserBonusSonDTO dto) {
            Assert.isNull(dto.getId(),"主键不能为空");
            UserBonusSonEntity entity=new UserBonusSonEntity();
            BeanUtil.copyProperties(dto,entity);
            Integer integer = userBonusSonDao.updateById(entity);
            return integer>0? RPCResult.success():RPCResult.fail();
    }

    @Override
    public ApiResult<List<UserBonusSonDTO>> querySonByMainIdApi(Long mainId) {
            List<UserBonusSonDTO> list=new ArrayList<>();
            List<UserBonusSonEntity> userBonusSonEntities = userBonusSonDao.querySonByMainId(mainId,UserConstant.IS_ZERO);
            if(CollectionUtil.isNotEmpty(userBonusSonEntities)){
                for (UserBonusSonEntity userBonusSonEntity : userBonusSonEntities) {
                    UserBonusSonDTO dto=new UserBonusSonDTO();
                    BeanUtil.copyProperties(userBonusSonEntity,dto);
                    list.add(dto);
                }
            }
            return RPCResult.success(list);
    }

    @Override
    public List<Map<String,Object>> queryBonusSonByMainId(Map map1,Map map2) {
            Assert.isNull(map1,"参数异常");
            Assert.isNull(map2,"参数异常");
            Assert.isNull(map1.get("mainId"),"主单id不能为空");
            List<Map<String, Object>> maps = userBonusSonDao.queryBonusSonByMainId(map1);

            if(CollectionUtil.isNotEmpty(maps)){
                List<UserBonusSonDTO> list=new ArrayList<>();
                logger.info("查询到同主单下其他细单存在相同的数据，参数：" + JSONUtil.toJsonStr(map1));
                return maps;
            }else{
                map2.putAll(map1);
                List<Map<String, Object>> maps1 = userBonusSonDao.queryBonusSonByMainId(map2);
                if(CollectionUtil.isNotEmpty(maps1)){
                    logger.info("查询到相同数据");
                    return maps1;
                }
            }

        return null;
    }

    @Override
    public ApiResult<UserBonusSonDTO> findByIdApi(Long id) {
            UserBonusSonEntity userBonusSonEntity = userBonusSonDao.selectById(id);
            if(userBonusSonEntity!=null){
                UserBonusSonDTO dto=new UserBonusSonDTO();
                BeanUtil.copyProperties(userBonusSonEntity,dto);
                return RPCResult.success(dto);
            }
        return RPCResult.fail();
    }

    @Override
    public ApiResult saveApi(UserBonusSonDTO dto) {
            if(dto==null){
                return RPCResult.fail();
            }
            UserBonusSonEntity entity=new UserBonusSonEntity();
            BeanUtil.copyProperties(dto,entity);
            return userBonusSonDao.insert(entity)>0?RPCResult.success():RPCResult.fail();
    }

    @Override
    public ApiResult<List<UserBonusSonDTO>> getUserBonusSonApi(Integer settingType, Long mainId) {
            QueryWrapper<UserBonusSonEntity> ew = new QueryWrapper<>();
            ew.eq("main_id",mainId);
            ew.eq("is_del", UserConstant.IS_F);
            List<String> sortReq = new ArrayList<>();
            if (settingType==0){
                //排序 分红：返点要求>团队日量（团队总量）>有效会员
                sortReq.add("rebate");
                sortReq.add("amount");
                sortReq.add("valid_member");
            }
            if (settingType==1){
                //排序  契约分红：金额>有效会员
                sortReq.add("amount");
                sortReq.add("valid_member");
            }
//            ew.orderBy(true,sortReq,false);
            ew.orderByAsc(settingType==0,"rebate","amount","valid_member");
            ew.orderByAsc(settingType==1,"amount","valid_member");
            List<UserBonusSonEntity> userBonusSonEntities = userBonusSonDao.selectList(ew);
            List<UserBonusSonDTO> dtoList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(userBonusSonEntities)){
                for (UserBonusSonEntity userBonusSonEntity : userBonusSonEntities) {
                    UserBonusSonDTO dto = new UserBonusSonDTO();
                    BeanUtil.copyProperties(userBonusSonEntity,dto);
                    dtoList.add(dto);
                }
            }
            return RPCResult.success(dtoList);
    }
}
