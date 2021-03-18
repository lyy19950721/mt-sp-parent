package com.mayikt.api.base;

import lombok.Data;

/**
 * @Classname BaseResponse
 * @Description TODO
 * @Date 2021/3/10 16:58
 * @Created by li.yy
 */
@Data
public class BaseResponse<T> {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回
     */
    private T data;
    // 分页

    public BaseResponse() {

    }

    public BaseResponse(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
