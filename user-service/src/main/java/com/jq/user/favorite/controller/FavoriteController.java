package com.jq.user.favorite.controller;

import com.jq.user.favorite.entity.FavoriteDTO;
import com.jq.user.favorite.service.FavoriteInnerService;
import com.jq.user.favorite.service.FavoriteService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/inner/user/favorite")
public class FavoriteController implements FavoriteService {

    @Resource
    private FavoriteInnerService favoriteInnerService;

    @Override
    public ApiResult addFavoriteApi(@RequestParam List<FavoriteDTO> dtoList) {
        return favoriteInnerService.addFavoriteApi(dtoList);
    }

    @Override
    public ApiResult cancelFavoriteApi(@RequestParam List<String> gameCodeList, @RequestParam Long userId) {
        return favoriteInnerService.cancelFavoriteApi(gameCodeList, userId);
    }

    @Override
    public ApiResult<List<FavoriteDTO>> searchFavoriteListApi(@RequestParam Long userId) {
        return favoriteInnerService.searchFavoriteListApi(userId);
    }
}
