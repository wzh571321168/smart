package com.ruoyi.common.enums;

public enum PicDiscernType {
    FACE(1, "face");

    private final Integer type;
    private final String info;

    PicDiscernType(Integer code, String info)
    {
        this.type = code;
        this.info = info;
    }

    public Integer getType()
    {
        return type;
    }

    public String getInfo()
    {
        return info;
    }
}