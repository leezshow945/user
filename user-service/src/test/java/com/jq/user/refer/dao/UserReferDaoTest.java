package com.jq.user.refer.dao;

import cn.hutool.json.JSONUtil;

import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.entity.UserReferEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReferDaoTest {

    @Resource
    private UserReferDao referDao;

    @Test
    public void queryAll (){
//        Map<String, Object> map = referDao.queryAll();
//        System.out.println(map);
    }




}
