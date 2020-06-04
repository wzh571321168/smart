package com.ruoyi.common.enums;

public enum  ModuleKeyEnum{
    STREEM_CALLBACK("STREEM_CALLBACK","streamPushHandler")
    ;
    private String code;
    private String beanName;

    ModuleKeyEnum(String code, String beanName) {
        this.code = code;
        this.beanName = beanName;
    }

    public String getCode() {
        return code;
    }

    public String getBeanName() {
        return beanName;
    }

    public static ModuleKeyEnum getEnum(String code) {
        for (ModuleKeyEnum moduleKeyEnum : ModuleKeyEnum.values()) {
            if (code == moduleKeyEnum.getCode()) {
                return moduleKeyEnum;
            }
        }
        return null;
    }
}
