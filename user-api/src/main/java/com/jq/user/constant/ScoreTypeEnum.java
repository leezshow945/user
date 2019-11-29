package com.jq.user.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum ScoreTypeEnum {

    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : ORDER
     * value : 每天首次下注
     * </pre>
     **/
    SCORE_TYPE_ORDER("SCORE_TYPE", "积分获取类型", "ORDER", "每天首次下注"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : SIGN
     * value : 每日签到
     * </pre>
     **/
    SCORE_TYPE_SIGN("SCORE_TYPE", "积分获取类型", "SIGN", "每日签到"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : AGENT_RECHARGE
     * value : 代理首次充值
     * </pre>
     **/
    SCORE_TYPE_AGENT_RECHARGE("SCORE_TYPE", "积分获取类型", "AGENT_RECHARGE", "代理首次充值"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : DEPOSIT
     * value : 人工存入
     * </pre>
     **/
    SCORE_TYPE_DEPOSIT("SCORE_TYPE", "积分获取类型", "DEPOSIT", "人工存入"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 获取
     * code : TACK_OUT
     * value : 人工取出
     * </pre>
     **/
    SCORE_TYPE_TACK_OUT("SCORE_TYPE", "积分获取类型", "TACK_OUT", "人工取出"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : RECHARGE_RATIO
     * value : 每日首次充值
     * </pre>
     **/
    SCORE_TYPE_RECHARGE_RATIO("SCORE_TYPE", "积分获取类型", "RECHARGE_RATIO", "充值兑换积分"),
    /**
     * <pre>
     * codeType : SCORE_TYPE
     * codeName : 积分获取类型
     * code : RECHARGE
     * value : 每日首次充值
     * </pre>
     **/
    SCORE_TYPE_RECHARGE("SCORE_TYPE", "积分获取类型", "RECHARGE", "每日首次充值");




    private String codeType;
    private String codeName;
    private String code;
    private String value;




    /**
     * 获得以参数codeType 为codeType的所有枚举 组装成map<code,value>
     *
     * @param codeType
     * @return
     * @throws
     */
    public static Map<String, String> getNameByCodeType(String codeType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (ScoreTypeEnum e : ScoreTypeEnum.values()) {
            if (e.getCodeType().equalsIgnoreCase(codeType)) {
                map.put(e.getCode(), e.getValue());
            }
        }
        return map;
    }


    /**
     * 通过code获取是否存在
     * @param code
     * @return
     */
    public static boolean isExist(String code) {
        for (ScoreTypeEnum e : ScoreTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获得以参数codeType 为codeType的所有枚举 组装成TreeMap<code,value> treeMap 按顺序排序
     *
     * @param codeType
     * @return
     */
    public static TreeMap<String, String> getNameByCodeTypeTreeMap(String codeType) throws Exception {
        TreeMap<String, String> map = new TreeMap<String, String>();
        for (ScoreTypeEnum e : ScoreTypeEnum.values()) {
            if (e.getCodeType().equalsIgnoreCase(codeType)) {
                map.put(e.getCode(), e.getValue());
            }
        }
        return map;
    }

    ScoreTypeEnum(String codeType, String codeName, String code, String value) {
        this.codeType = codeType;
        this.codeName = codeName;
        this.code = code;
        this.value = value;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    }
