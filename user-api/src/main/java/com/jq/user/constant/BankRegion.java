package com.jq.user.constant;

public enum BankRegion {

    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 5
     * value : 西南地区
     * </pre>
     **/
    BANK_REGION_5("BANK_REGION", "区域分类", "5", "西南地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 0
     * value : 全国性银行
     * </pre>
     **/
    BANK_REGION_0("BANK_REGION", "区域分类", "0", "全国性银行"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 2
     * value : 华南地区
     * </pre>
     **/
    BANK_REGION_2("BANK_REGION", "区域分类", "2", "华南地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 7
     * value : 西北地区
     * </pre>
     **/
    BANK_REGION_7("BANK_REGION", "区域分类", "7", "西北地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 8
     * value : 外资银行
     * </pre>
     **/
    BANK_REGION_8("BANK_REGION", "区域分类", "8", "外资银行"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 1
     * value : 华东地区
     * </pre>
     **/
    BANK_REGION_1("BANK_REGION", "区域分类", "1", "华东地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 9
     * value : 农商银行
     * </pre>
     **/
    BANK_REGION_9("BANK_REGION", "区域分类", "9", "农商银行"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 3
     * value : 华北地区
     * </pre>
     **/
    BANK_REGION_3("BANK_REGION", "区域分类", "3", "华北地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 4
     * value : 华中地区
     * </pre>
     **/
    BANK_REGION_4("BANK_REGION", "区域分类", "4", "华中地区"),
    /**
     * <pre>
     * codeType : BANK_REGION
     * codeName : 区域分类
     * code : 6
     * value : 东北地区
     * </pre>
     **/
    BANK_REGION_6("BANK_REGION", "区域分类", "6", "东北地区");

    private String codeType;
    private String codeName;
    private String code;
    private String value;


    private BankRegion(String codeType, String codeName, String code, String value) {
        this.codeType = codeType;
        this.codeName = codeName;
        this.code = code;
        this.value = value;
    }
}
