package com.jq.user.favorite.service;

import com.jq.user.favorite.entity.FavoriteDTO;
import com.jq.user.favorite.fallbackfactory.FavoriteServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user",path = "/inner/user/favorite",url = "${feign-url.user:}",fallbackFactory = FavoriteServiceFallbackFactory.class)
public interface FavoriteService {

    /**
     * @Author: levi
     * @Descript: 添加收藏列表
     * @param dtoList 收藏集合
     * @Date: 2018/7/12
     */
    @PostMapping(value = "addFavoriteApi")
    ApiResult addFavoriteApi(@RequestParam List<FavoriteDTO> dtoList);

    /**
     * @param gameCodeList 游戏code集合
     * @param userId       用户id
     * @Author: levi
     * @Descript: 取消收藏列表
     * @Date: 2018/7/12
     */
    @PutMapping(value = "cancelFavoriteApi")
    ApiResult cancelFavoriteApi(@RequestParam List<String> gameCodeList, @RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 查询收藏列表
     * @param userId 用户id
     * @Date: 2018/7/12
     */
    @GetMapping(value = "searchFavoriteListApi")
    ApiResult<List<FavoriteDTO>> searchFavoriteListApi(@RequestParam Long userId);
}
