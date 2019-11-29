package com.jq.user.oldbonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.oldbonus.entity.UserBonusEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserBonusDao extends BaseMapper<UserBonusEntity> {

    /**
     * 通过用户指定的条件查询数据分页
     *
     * @param entity
     * @return
     * @author wgc
     */
    public Page<Map<String, Object>> queryUserBonusByConditionsPage(UserBonusEntity entity, int pageNum, int pageSize, String rangeString, String sortField, String sortRule);

    /**
     * 获取所有数据的分页,页面初始化
     *
     * @return
     * @author wgc
     */
    public Page<Map<String, Object>> queryUserBonusPage(int pageNum, int pageSize);

    /**
     * 根据游戏期号和类型查询游戏分红列表数量
     *
     * @param periods
     * @param dataType
     * @return
     * @author xcc
     */
    long queryUserBonus(String periods, String dataType, String mainId, String examineState);

    /**
     * 分页查询日工资、契约日工资
     * @param bonusVO
     * @param level 层级
     * @param orderField    排序条件
     * @param orderDirection
     * @param pageNum
     * @param pageSize
     * @return
     * @author zcxu
     */
//	Page<UserBonusVO> queryBonusDailyPage(UserBonusVO bonusVO, String level, String orderField, String orderDirection, int pageNum, int pageSize);

    /**
     * 分页查询分红、契约分红
     * @param bonusVO
     * @param level 层级
     * @param orderField    排序条件
     * @param orderDirection
     * @param pageNum
     * @param pageSize
     * @return
     * @Author: zcxu
     */
//	Page<UserBonusVO> queryBounsControllerPage(UserBonusVO bonusVO, String level, String orderField, String orderDirection, int pageNum, int pageSize);

    /**
     *
     * @Title: queryUserTeamSales
     * @Description: 查找用户团队销售
     * @param userId 用户id
     * @param gameType 游戏类型 （高频彩，棋牌，六合彩等大类code）
     * @param gameCode 游戏代码 （六合彩返点类型取字典表对应数字rebate1/2/3/4...，其他游戏大类可为空）
     * @param beginTime 开始时间，格式：年月日
     * @param endTime 结束时间，格式：年月日
     * @return
     * @return List<UserTeamSalesVO>    返回类型
     * @date 2017年10月4日 下午5:08:42
     * @author jfChen
     */
//	public List<UserTeamSalesVO> queryUserTeamSales(String userId, String gameType, String gameCode, String beginTime, String endTime);


    /**
     * 批量添加分红
     *
     * @param recordList
     * @return
     */
//	GenericResult batchAddRecord(List<UserBonusModel> recordList);


    /**
     * 根据条件查询游戏分红列表
     *
     * @param periods 期数
     * @param dataType 数据类型(0:分红,1:契约分红,2:日工资,3:契约日工资)
     * @param examineState 审核状态(0:临时，1:锁定，2:已审核)
     * @param siteId 站点id
     *
     * @return
     */
//	List<UserBonusModel> queryBonusList(String periods, String dataType, String examineState, String siteId,
//                                        String mainId, String toUserId);

    /**
     * 根据bonusVO内的条件分页查询
     *
     * @param bonusVO
     * @param pageCurrent
     * @param pageSize
     * @return
     * @author zcxu
     */
//	Page<LotUserBonusVO> selectByMapAndPage(LotUserBonusVO bonusVO, Integer pageCurrent, Integer pageSize);


    /**
     * 查询团队销量by trade系统
     *
     * 包括代理自己的投注（默认）
     * @param userId
     * @param gameType
     * @param gameCode
     * @param beginTime
     * @param endTime
     * @return
     */
//	List<UserTeamSalesVO> queryUserTeamByTrade(String userId, String gameType, String gameCode, String beginTime,
//                                               String endTime);

    /**
     * 查询团队销量by trade系统
     *
     * 根据需求查询是否包含代理自己的投注
     *
     * @param userId
     * @param gameType
     * @param gameCode
     * @param beginTime
     * @param endTime
     * @param isAgent 是否包含代理 是true,否false
     *
     * @return
     */
//	List<UserTeamSalesVO> queryUserTeamByTrade(String userId, String gameType, String gameCode, String beginTime,
//                                               String endTime, boolean isAgent);

    /**
     * 获取团队销量列表
     *
     * @param userId
     * @param gameType
     * @param gameCode
     * @param beginTime
     * @param endTime
     * @param orderBy 按happen_date排序 desc降序，asc升序
     * @return
     * @date 2017年10月19日 下午7:58:33
     * @author jfChen
     */
//	List<UserTeamSalesVO> queryAllUserTeam(String userId, String gameType, String gameCode, String beginTime,
//                                           String endTime, String orderBy);

    /**
     * 根据多个id，查询多条记录
     * @param ids
     * @param examineState
     * @return
     * @author zcxu
     */
//	List<UserBonusModel> queryUserBonusByIds(String ids, String examineState);

    /**
     * 根据条件查询接收用户统计数据
     * @param siteId
     * @param toUserId
     * @param gameId
     * @return
     */
//	Map<String,Object> querySumByArgs(String siteId, String toUserId, String gameId, String beginDate, String endDate);

    /**
     * 根据userId，查询相关待结算数据
     * @param userId
     * @param siteId
     * @param flage  true:根据userid;flase:根据touserid
     * @return
     * @author xc
     */
//	List<UserBonusModel> queryUserBonusByUserId(String userId, String siteId, boolean flag, int dataType);

    /**
     * 查询迁移记录，按修改时间降序排序
     * @return
     */
//	public List<UserBonusModel> queryUserBonusPerTransferList(BonusPreTransferVO bonusPreTransferVO);
    public List<Map<String, Object>> queryHighUserListNo(String beginTime,
                                                         String endTime);

    /**
     * 查询有投注记录的代理会员
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> queryHighUserList(String beginTime, String endTime);

    /**
     * 根据userId，查询相关待结算数据
     * @return
     * @author xc
     */
    List<UserBonusDTO> queryUserBonusByUserIdApi(Map map);

    /**
     * 分页查询 分红记录
     * @param dto
     * @param pagination
     * @return
     */
    List<UserBonusDTO> queryUserBonusDividByPage(@Param("dto") UserBonusDTO dto, IPage page);

    /**
     * 根据id集合 审核状态查询
     *
     * @param ids
     * @param status
     * @return
     */
    List<UserBonusDTO> selectBatchIdsAndStatus(@Param("ids") List ids, @Param("examineState") Integer status);

    /**
     * 批量保存分红记录
     * @param list
     */
    void insertBatch(List<UserBonusDTO> list);

    /**
     * 根据用户id 类型查询奖金记录
     * @param userId 用户id
     * @param dataType 奖金类型
     * @return
     */
    Long getTotalBonus(@Param("userId") Long userId, @Param("dataType") Integer dataType);

    Long getTotalMakeWater(@Param("userId") Long userId);

    /**
     *
     * 获取用户指定期数内的日工资/契约日工资总计
     * @param dataType 2 日工资  3 契约日工资
     * @param countPeriods 期号列表
     * @param toUserId 用户
     * @param gameCategoryId 游戏编码
     * @param playType 游戏玩法
     * @param ids 用户id集合
     * @return
     */
    Long selectDailyBonus(@Param("dataType") int dataType,@Param("countPeriods") List<String> countPeriods,@Param("toUserId") Long toUserId,@Param("gameCategoryId") String gameCategoryId,@Param("playType") String playType,@Param("ids") List<Long> ids);

    /**
     * 批量获取用户日工资
     * @param reqIds 用户id集合
     * @param dataType 日工资
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Map<String,Long>> selectUsersDailyBonus(@Param("ids") List<Long> reqIds, @Param("dataType") int dataType,@Param("beginTime") String beginTime,@Param("endTime") String endTime);
}
