package com.ruoyi.common.vo;

import lombok.Data;

@Data
public class NettyMsgVo<T> {
    private String moduleKey;
    private T data;

    public NettyMsgVo(String moduleKey, T data) {
        this.moduleKey = moduleKey;
        this.data = data;
    }
}
