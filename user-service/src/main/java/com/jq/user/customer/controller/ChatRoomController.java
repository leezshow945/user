package com.jq.user.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.customer.service.ChatRoomInnerService;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.exception.UserException;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Brady
 * @Description :
 * @Date: Create in 9:45 2018/12/28
 */

@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    @Resource
    private ChatRoomInnerService chatRoomInnerService;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private SiteService siteService;

    @GetMapping(value = "/getTokenInfo/{token}")
    public String getTokenInfo(@PathVariable String token){
        if(StrUtil.isBlank(token)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"token参数缺失");
        }
        return chatRoomInnerService.getTokenInfo(token);
    }

    @GetMapping(value = "/queryUserInfo/{siteId}")
    public List<Map<String,Object>> queryUserInfo(@PathVariable Long siteId){
        if(ObjectUtil.isNull(siteId)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        List<Map<String,Object>> resultList = chatRoomInnerService.queryUserInfo(siteId);
        return resultList;
    }

    @PostMapping(value = "/queryUserInfo")
    public List<Map<String,Object>> queryUserInfo(@RequestParam("idList") List<Long> idList){
        if(null == idList || idList.size() == 0){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        List<Map<String,Object>> resultList = chatRoomInnerService.queryUserInfo(idList);
        return resultList;
    }

    @GetMapping(value = "/getUserInfo/{id}")
    public Map<String,Object> getUserInfo(@PathVariable("id") Long id){
        if(ObjectUtil.isNull(id)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        Map<String,Object> resultList = chatRoomInnerService.getUserInfo(id);
        return resultList;
    }

    @GetMapping(value = "/rsa")
    public String getPublicRsaKey(){
        String rsaPublicKey = userInnerService.getRSAPublicKey();
        return rsaPublicKey;
    }

    @PostMapping(value = "/login")
    public Map<String,Object> loginSubmit(@RequestParam(value="userName") String userName, @RequestParam(value="password") String password,
                                          @RequestHeader("siteId") String siteId){
        if(StrUtil.isBlank(userName)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"userName参数为空");
        }
        if(StrUtil.isBlank(password)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"password参数为空");
        }
        if(StrUtil.isBlank(siteId)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数为空");
        }
        Map<String,Object> resultMap = chatRoomInnerService.loginSubmit(userName,password,Long.parseLong(siteId));

        return resultMap;
    }

    @GetMapping(value = "/getDetailUserInfo/{id}")
    public Map<String,Object> getDetailUserInfo(@PathVariable("id") Long id,
                                                @RequestParam("siteId") Long siteId){
        if(ObjectUtil.isNull(id)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"id参数缺失");
        }
        if(ObjectUtil.isNull(siteId)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        ApiResult<SiteDTO> siteByIdApi = siteService.findSiteByIdApi(siteId);
        if(!RPCResult.checkApiResult(siteByIdApi)){
            throw new UserException(UserCodeEnum.SITE_NOT_EXIST.getCode(),UserCodeEnum.SITE_NOT_EXIST.getMessage());
        }
        Map<String,Object> resultList = chatRoomInnerService.getDetailUserInfo(id,siteByIdApi.getData().getSiteCode());
        return resultList;
    }

    @PostMapping(value = "/queryDetailUserInfo")
    public List<Map<String,Object>> queryDetailUserInfo(@RequestParam("idList") List<Long> idList,
                                                        @RequestHeader("siteId") Long siteId){
        if(ObjectUtil.isNull(idList) || idList.size() == 0){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"idList参数缺失");
        }
        if(ObjectUtil.isNull(siteId)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        ApiResult<SiteDTO> siteByIdApi = siteService.findSiteByIdApi(siteId);
        if(!RPCResult.checkApiResult(siteByIdApi)){
            throw new UserException(UserCodeEnum.SITE_NOT_EXIST.getCode(),UserCodeEnum.SITE_NOT_EXIST.getMessage());
        }
        List<Map<String,Object>> resultList = chatRoomInnerService.queryDetailUserInfo(idList,siteByIdApi.getData().getSiteCode());
        return resultList;
    }

    /**
     * @Author: Brady
     * @Descript:根据用户Id和站点id获取用户资金相关记录（id,realName,totalAmount,profit,income）
     * @Date: 2019/1/22
     */

    @PostMapping(value = "/queryCashInfo")
    public List<Map<String,Object>> queryCashInfo(@RequestParam("idList") List<Long> idList,
                                                  @RequestHeader("siteId") Long siteId){

        if(ObjectUtil.isNull(idList) || idList.size() == 0){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"idList参数缺失");
        }
        if(ObjectUtil.isNull(siteId)){
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode(),"siteId参数缺失");
        }
        ApiResult<SiteDTO> siteByIdApi = siteService.findSiteByIdApi(siteId);
        if(!RPCResult.checkApiResult(siteByIdApi)){
            throw new UserException(UserCodeEnum.SITE_NOT_EXIST.getCode(),UserCodeEnum.SITE_NOT_EXIST.getMessage());
        }
        List<Map<String,Object>> resultList = chatRoomInnerService.queryCashInfo(idList,siteByIdApi.getData().getSiteCode());
        return resultList;
    }

}
