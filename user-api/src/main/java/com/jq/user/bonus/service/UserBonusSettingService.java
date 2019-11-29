package com.jq.user.bonus.service;

import com.jq.user.bonus.dto.UserBonusMainSettingDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.jq.user.bonus.fallbackfactory.UserBonusSettingServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/bonusSetting",url = "${feign-url.user:}",fallbackFactory = UserBonusSettingServiceFallbackFactory.class)
public interface UserBonusSettingService {

    /**
     * @Author: levi
     * @Descript: 获取日工资设置列表
     * @Date: 2018/6/28
     */
    @GetMapping(value = "queryBonusSetByLevelApi")
    ApiResult<Map<String, Object>> queryBonusSetByLevelApi(@RequestParam Long siteId, @RequestParam Integer level,
                                                           @RequestParam Integer settingType);

    /**
     * @param settingType 2-日工资 3-契约日工资
     * @param mainId      主单id
     * @Author: levi
     * @Descript: 获取日工资、契约日工资细单
     * @Date: 2018/6/28
     */
    @GetMapping(value = "getBonusSettingApi")
    ApiResult<List<UserBonusSettingDTO>> getBonusSettingApi(@RequestParam Integer settingType, @RequestParam Long mainId);
    /**
     * @param settingType 2-日工资 3-契约日工资
     * @param id      setting表id
     * @Author: brady
     * @Descript: 获取日工资、契约日工资细单
     * @Date: 2018/6/28
     */
    @GetMapping(value = "getBonusSettingByIdApi")
    ApiResult<List<UserBonusSettingDTO>> getBonusSettingByIdApi(@RequestParam Integer settingType, @RequestParam Long id);
    /**
     * @param settingType 2-日工资 3-契约日工资
     * @param mainId      主单id
     * @Author: levi
     * @Descript: 获取日工资、契约日工资细单
     * @Date: 2018/6/28
     */
    @GetMapping(value = "getBonusMapApi")
    ApiResult<List<Map<String,Object>>> getBonusMapApi(@RequestParam Integer settingType, @RequestParam Long mainId);


    /**
     * Author: Brady
     * Description:查询层级日工资设置
     * Date: 2018/7/2
     */
    @GetMapping(value = "bonusSetByLevelApi")
    ApiResult<Map<String, Object>> queryBonusSetByLevelApi(@RequestParam Long siteId, @RequestParam String pcode,
                                                           @RequestParam Integer rebateLevel, @RequestParam int settingType);

    /**
     * Author: Brady
     * Description: 新增工资
     * Date: 2018/7/3
     */
    @DeleteMapping(value = "deleteApi")
    ApiResult deleteApi(@RequestParam Long siteId,@RequestParam Long id,
                        @RequestParam Long mainId,@RequestParam Long updateByUserId,
                        @RequestParam String ip,@RequestParam String url);

    /**
     * @Author: levi
     * @Descript: 新增日工资子单
     * @Date: 2018/7/9
     */
    @PostMapping(value = "saveApi")
    ApiResult saveApi(@RequestBody UserBonusSettingDTO dto);

    /**
     * @Author: levi
     * @Descript: 修改日工资子单
     * @Date: 2018/7/9
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody UserBonusSettingDTO dto);

    /**
     * @Author: Brady
     * @Descript: 获取mainId相关数据
     * @Date: 2018/7/9
     */
    @GetMapping(value = "queryUserBonusSettingApi")
    ApiResult<List<Map<String,Object>>> queryUserBonusSettingApi(@RequestParam Long mainId);

    @GetMapping(value = "querySettingByQy")
    ApiResult<List<Map<String, Object>>> querySettingByQy(@RequestParam String teamDailyAmount,@RequestParam Integer validMember,
                                                          @RequestParam Integer bonus,@RequestParam Integer daysPer,
                                                          @RequestParam Long mainId,@RequestParam Long id,
                                                          @RequestParam Integer bonusMode);

    @GetMapping(value = "querySettingByQyApi")
    ApiResult<List<Map<String, Object>>> querySettingByQyApi(@RequestParam String teamDailyAmount,
                                                             @RequestParam Long mainId,
                                                             @RequestParam  Long id);

    /**
     * 修改日工资设置 包含新增
     * @param dto
     * @return
     */
    @PutMapping(value = "updateUserBonusSetting")
    ApiResult updateUserBonusSetting(@RequestBody UserBonusMainSettingDTO dto);



}
