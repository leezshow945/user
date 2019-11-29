package com.jq.user.customer.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Brady
 * @Description :
 * @Date: Create in 9:46 2018/12/28
 */
public interface ChatRoomInnerService {

    /**
     * @Author: Brady
     * @Descript: 获取token信息
     * @Date: 2018/12/28
     */
    String getTokenInfo(String token);

    /**
     * @Author: Brady
     * @Descript: 根据站点获取所以用户信息
     * @Date: 2019/1/4
     */
    List<Map<String,Object>> queryUserInfo(Long siteId);

    /**
     * @Author: Brady
     * @Descript: 根据用户id获取所以用户信息
     * @Date: 2019/1/4
     */
    List<Map<String,Object>> queryUserInfo(List<Long> idList);

    /**
     * @Author: Brady
     * @Descript: 用户登录
     * @Date: 2019/1/4
     */
    Map<String,Object> loginSubmit(String userName, String password, Long siteId);

    /**
     * @Author: Brady
     * @Descript:根据id获取用户信息
     * @Date: 2019/1/21
     */
    Map<String,Object> getUserInfo(Long id);

    /**
     * @Author: Brady
     * @Descript:根据用户id获取用户详细信息（基本信息+资金相关+注册时间）
     * @Date: 2019/1/21
     */
    Map<String,Object> getDetailUserInfo(Long id,String siteCode);

    /**
     * @Author: Brady
     * @Descript: 根据用户id集合获取用户详细信息（基本信息+资金相关+注册时间）
     * @Date: 2019/1/22
     */
    List<Map<String,Object>> queryDetailUserInfo(List<Long> idList, String siteCode);

    /**
     * @Author: Brady
     * @Descript:根据用户Id和siteCode获取用户资金相关记录（id,realName,totalAmount,profit,income）
     * @Date: 2019/1/22
     */
    List<Map<String,Object>> queryCashInfo(List<Long> idList, String siteCode);
}
