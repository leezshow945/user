package com.jq.user.customer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Test
    public void test(){

    }
//    public static void main(String[] args) {
//        List<UserRebateEntity> entityList = new ArrayList<UserRebateEntity>();//userRebateDao.selectBatchIds(idList);
//        UserRebateEntity e1 = new UserRebateEntity();
//        e1.setId(1045915686744436738L);
//        e1.setPath("1031885121722597378,1037259474595295234,1039392833672916994");
//
//        UserRebateEntity e2 = new UserRebateEntity();
//        e2.setId(1039392833672916994L);
//        e2.setPath("1031885121722597378,1037259474595295234");
//
//        UserRebateEntity e3 = new UserRebateEntity();
//        e3.setId(1036141293650653185L);
//        e3.setPath("1031885121722597378");
//
//        UserRebateEntity e4 = new UserRebateEntity();
//        e4.setId(1037697066411307009L);
//        e4.setPath("1031885121722597378");
//
//        UserRebateEntity e5 = new UserRebateEntity();
//        e5.setId(1037255714695360514L);
//        e5.setPath("1031885121722597378");
//
//        UserRebateEntity e6 = new UserRebateEntity();
//        e6.setId(1037620518580727810L);
//        e6.setPath("1031885121722597378,1036472967705997314");
//
//        UserRebateEntity e7 = new UserRebateEntity();
//        e7.setId(1043053879986040833L);
//        e7.setPath("1031885121722597378");
//
//        UserRebateEntity e8 = new UserRebateEntity();
//        e8.setId(1052461906853965826L);
//        e8.setPath("1031885121722597378,1043053879986040833");
//
//        UserRebateEntity e9 = new UserRebateEntity();
//        e9.setId(1033670618058928129L);
//        e9.setPath("1031885121722597378");
//
//        UserRebateEntity e10 = new UserRebateEntity();
//        e10.setId(1035823647860273153L);
//        e10.setPath("1031885121722597378,1036141293650653185");
//
//        UserRebateEntity e11 = new UserRebateEntity();
//        e11.setId(1035823722317557762L);
//        e11.setPath("1031885121722597378,1036141293650653185,1035823647860273153");
//
//        UserRebateEntity e12 = new UserRebateEntity();
//        e12.setId(1037259474595295234L);
//        e12.setPath("1031885121722597378");
//
////        entityList.add(e1);
////        entityList.add(e2);
////        entityList.add(e3);
////        entityList.add(e4);
////        entityList.add(e5);
////        entityList.add(e6);
////        entityList.add(e7);
////        entityList.add(e8);
////        entityList.add(e9);
////        entityList.add(e10);
////        entityList.add(e11);
////        entityList.add(e12);
//        entityList.add(e5);
//        entityList.add(e2);
//        entityList.add(e9);
//        entityList.add(e12);
//        entityList.add(e1);
//        entityList.add(e6);
//        entityList.add(e7);
//        entityList.add(e11);
//        entityList.add(e3);
//        entityList.add(e10);
//        entityList.add(e8);
//        entityList.add(e4);
//
//        //总的代理线Map
//        Map<Long, Map> proxyLine = new HashMap<>();
//        for(UserRebateEntity entity : entityList){
//            if(StrUtil.isEmpty(entity.getPath())){
//                continue;
//            }
//            //每个用户的上级
//            String[] paths = entity.getPath().split(",");
//            for(int i = 0; i <paths.length;i++ ){
//                //当前用户所属的代理线
//                String proxy = paths[i];
//                //代理线中所有的下级
//                Map<Long,Long> userLine = proxyLine.get(NumberUtils.toLong(proxy));
//                //如果代理线已存在，则从代理线Map中获取
//                if(userLine == null){
//                    userLine = new HashMap<>();
//                }
//                userLine.put(entity.getId(),entity.getId());
//                for(int j = i; j < paths.length; j ++){
//                    String id = paths[j];
//                    //将用户所有上级存入代理线中，包含了去重于合并操作
//                    userLine.put(NumberUtils.toLong(id),NumberUtils.toLong(id));
//                }
//                proxyLine.put(NumberUtils.toLong(proxy),userLine);
//            }
//            if(!proxyLine.containsKey(entity.getId())){
//                Map self = new HashMap();
//                self.put(entity.getId(),entity.getId());
//                proxyLine.put(entity.getId(),self);
//            }
//        }
//
//        //组装数据格式
//        Map<Long,List<Long>> result = new HashMap<>();
//        for(Long key : proxyLine.keySet()){
//            Map child = proxyLine.get(key);
//            result.put(key,new ArrayList<>(child.keySet()));
//        }
//
//        System.out.println(result);
//    }
}
