package com.jq.user.customer.service;

import com.jq.user.constant.UserConstant;
import com.jq.user.favorite.entity.FavoriteDTO;
import com.jq.user.favorite.service.FavoriteService;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteServiceTest {

    @Resource
    private FavoriteService favoriteService;

    @Test
    public void addFavoriteApiTest() {
        List<FavoriteDTO> list = new ArrayList<>();
        FavoriteDTO dto = new FavoriteDTO();
        dto.setRemark("remark");
        dto.setCreateBy("createBy");
        dto.setUpdateBy("createBy");
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setIsDel(UserConstant.IS_F);
        dto.setIsEnable(UserConstant.IS_T);
        dto.setSiteId(1011868438377877506L);
        dto.setUserId(1014030654208299010L);
        dto.setGameCode("imqqffc");
        dto.setSiteCode("jq-0");
        list.add(dto);
        ApiResult apiResult = favoriteService.addFavoriteApi(list);
        System.out.println(apiResult);
    }

    @Test
    public void cancelFavoriteApiTest() {
        List<String> list = new ArrayList<>();
        list.add("gameCod");
        Long userId = 1014030654208299010L;
        ApiResult apiResult = favoriteService.cancelFavoriteApi(list, userId);
        System.out.println(apiResult);
    }

    @Test
    public void searchFavoriteListApi() {
        Long userId = 1014030654208299010L;
        ApiResult<List<FavoriteDTO>> listApiResult = favoriteService.searchFavoriteListApi(userId);
        List<FavoriteDTO> data = listApiResult.getData();
        System.out.println(data);
    }
}
