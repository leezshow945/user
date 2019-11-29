package com.jq.user.oldbonus.constant;

public enum BonusRule {
    WIN_LOSE(1, "盈亏"),
    DAILY(2, "日量"),
    TOTAL(3, "总量");

    private Integer code;
    private String message;

    BonusRule(Integer code, String messge) {
        this.code = code;
        this.message = messge;
    }

    public static boolean hasCode(Integer code) {
        for (BonusRule bonusRule : BonusRule.values()) {
            if (bonusRule.getCode().equals(code)) {
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
