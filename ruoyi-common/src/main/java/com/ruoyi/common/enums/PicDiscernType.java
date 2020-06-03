package com.ruoyi.common.enums;

public enum PicDiscernType {
    FACE("1", "face"),
    CHARACTOR("2", "charactor"),
    COMPARE("3", "compare");

    private final String type;
    private final String info;

    PicDiscernType(String code, String info)
    {
        this.type = code;
        this.info = info;
    }

    public String getType()
    {
        return type;
    }

    public String getInfo()
    {
        return info;
    }
}
