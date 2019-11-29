package com.jq.user.support;

public class AddressUtil {

    // 省
    private static final String PROVINCE = "河北、山西、吉林、辽宁、黑龙江、陕西、甘肃、青海、山东、福建、浙江、台湾、河南、湖北、湖南、江西、江苏、安徽、广东、海南、四川、贵州、云南";
    // 直辖市
    private static final String MUNICIPALITY = "北京,天津,上海,重庆";
    // 自治区
    private static final String REGION = "西藏,内蒙古,宁夏,新疆,广西";
    // 特别行政区
    private static final String SPECIAL_REGION = "香港,澳门";


    public static boolean isProvince(String address) {
        return PROVINCE.contains(address);
    }

    public static boolean isMunicipality(String address) {
        return MUNICIPALITY.contains(address);
    }

    public static boolean isRegion(String address) {
        return REGION.contains(address);
    }

    public static boolean isSpecialRegion(String address) {
        return SPECIAL_REGION.contains(address);
    }

}
