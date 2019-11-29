package com.jq.user.support;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<?> listCopy(List<?> sourceList, Class target) {
        List<Object> objects = new ArrayList<>();
        Object targetBean;
        if (sourceList.size() == 0) {
            return objects;
        }
        for (Object entity : sourceList) {
            try {
                targetBean = target.newInstance();
                BeanUtils.copyProperties(entity, targetBean);
                objects.add(targetBean);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }
}
