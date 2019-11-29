package com.jq.user.oldbonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.oldbonus.entity.UserBonusSettingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface UserBonusSettingDao extends BaseMapper<UserBonusSettingEntity> {

    /**
     * 获取对应层级数据
     *
     * @param rebateLevel
     * @parem type
     * @author XC
     */
    List<Map<String, Object>> queryBonusSetByLevel(@Param("rebateLevel") Integer rebateLevel, @Param("settingType") int settingType, @Param("siteId") Long siteId);

    /*
     * 根据条件查询子单
     * @author XC
     */
    List<Map<String, Object>> queryBonusSettingByMainId(@Param("playType") String playType, @Param("gameCategoryId") String gameCategoryId, @Param("rebateLevel") Integer rebateLevel,
                                                        @Param("mainId") Long mainId, @Param("rebate") String rebate, @Param("validMember") String validMember, @Param("dailyAmount") String dailyAmount, @Param("actualLose") String actualLose, @Param("bonus") String bonus,
                                                        @Param("daysPer") String daysPer, @Param("ykPer") String ykPer, @Param("sonId") String sonId, @Param("bonusMode") Integer bonusMode);

    List<Map<String,Object>> queryBonusSetting(@Param("playType") String playType, @Param("gameCategoryId") String gameCategoryId, @Param("rebateLevel") Integer rebateLevel,
                                               @Param("mainId") Long mainId, @Param("rebate") String rebate, @Param("validMember") String validMember, @Param("dailyAmount") String dailyAmount, @Param("actualLose") String actualLose,
                                               @Param("sonId") String sonId);

/////////////////////////////////////////////////////////////////////////////////////////////////////	

    /**
     * 查询对应层级的数据，rebateLevel为空表示所有层级
     *
     * @param rebateLevel
     * @return
     * @author wgc
     */
    public List<UserBonusSettingEntity> queryUserBonusSettingByRebateLevel(String rebateLevel, Date yesterdayTime);

    /**
     * 更新是否自动派发
     *
     * @param gameCategoryId
     * @param state
     * @author wgc
     */
    public void updateUserBonusSettingDistributeById(String gameCategoryId, int state);


    /**
     * 批量添加数据
     *
     * @author wgc
     */
    public void batchSaveUserBonusSetting(List<UserBonusSettingEntity> model);

    /**
     * 更新日工资设置
     *
     * @param entity
     * @author wgc
     */
    public void updateUserBonusSettingByUserId(UserBonusSettingEntity entity);

    /**
     * 根据指定的id删除数据
     *
     * @param id
     * @author wgc
     */
    public void updateUSerBonusSettingIsDelById(String id);

    /**
     * 获取所有的setting数据
     *
     * @return
     * @author wgc
     */
    public List<?> queryUserBonusSettingInit();

    /**
     * 获取最后一条数据的id
     *
     * @return
     * @author wgc
     */
    public List<?> queryBonusSettingFinalId(String gameId);


    /**
     * 多表查询，获取契约工资设置信息
     *
     * @return
     * @author zcxu
     */
    public List<Map<String, Object>> queryUserBonusMainMultipleData(@Param("mainId") Long mainId, @Param("settingType")Integer settingType, @Param("siteId")Long siteId, Page page, @Param("superAccount") String superAccount, @Param("subAccount") String subAccount, @Param("gameType") String gameType);

    /**
     * 查询契约工资列表详情
     *
     * @param siteId
     * @param mainId
     * @return
     * @author wgc
     */
    public List<?> queryUserContractSalary(String siteId, String mainId);


    /**
     * 删除记录
     *
     * @param objId
     * @author wgc
     */
    public void updateUserBonusSettingIsDel(String objId);

    /**
     * 查询用户日工资设置
     *
     * @param settingType
     * @return
     */
    List<UserBonusSettingEntity> queryUserBonusSetting(String settingType);

    /**
     * 获取第一条返点记录
     *
     * @param mainId
     * @return
     */
    UserBonusSettingEntity getUserBonusSetting(Long mainId);

    /**
     * 根据mainId查询所有细单
     *
     * @param mainId
     * @return
     * @author brady
     */
    List<UserBonusSettingEntity> querySettingByMainId(@Param("mainId") Long mainId,@Param("isDel") Integer isDel);


    /**
     * 根据条件查找重复契约日工资
     *
     * @return
     * @author xc
     */
    List<Map<String, Object>> querySettingByQy(String teamDailyAmount, String validMember, String bonus, String daysPer,
                                               String mainId, String id, String bonusMode);


    List<Map<String,Object>> queryUserBonusSettingApi(@Param("mainId") Long mainId);

    List<Map<String,Object>> querySettingByQy(@Param("teamDailyAmount")String teamDailyAmount,@Param("validMember") Integer validMember,@Param("bonus") Integer bonus,@Param("daysPer") Integer daysPer,@Param("mainId") Long mainId,@Param("id") Long id,@Param("bonusMode") Integer bonusMode);

    List<Map<String,Object>> querySettingByQyApi(@Param("teamDailyAmount")String teamDailyAmount,@Param("mainId") Long mainId,@Param("id") Long id);
}
