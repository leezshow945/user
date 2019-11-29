package com.jq.user.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtil {

    /**
     * @param current        当前页
     * @param size           分页大小
     * @param orderDirection 排序声明
     * @param orderField     排序字段
     * @Author: levi
     * @Descript: 分页工具
     * @Date: 2019/1/24
     */
    public static Page buildPage(Integer current, Integer size, String orderDirection, String orderField) {
        Page page = new Page(current, size);
        if ("asc".equalsIgnoreCase(orderDirection)) {
            page.setAsc(orderField);
        } else {
            page.setDesc(orderField);
        }
        return page;
    }

    public static Page buildPage(Integer current, Integer size) {
        return new Page(current, size);
    }
}
