package com.jq.user.oldbonus.constant;

public enum BonusStrategy {
    UN_ACCUMULATE(1, "盈亏不累计结算"),
    BALANCE(2, "盈亏永久结算"),
    ACCUMULATE_LAST(3, "盈亏累计上一期"),
    ACC_HALF_MONTH(4, "盈亏下半月累计结算");

    private Integer code;
    private String message;

    BonusStrategy(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean hasCode(Integer code) {
        for (BonusStrategy bonusStrategy : BonusStrategy.values()) {
            if (bonusStrategy.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
