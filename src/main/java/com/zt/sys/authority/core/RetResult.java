package com.zt.sys.authority.core;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: RetResult
 * @Description: 返回对象实体
 * @Author: Eric
 * @Date: Created in 2018/8/6 14:54
 */
public class RetResult<T> {

    @ApiModelProperty(value="返回值",name="code",example="200/201/400/401/403/404/500")
    public int code;

    @ApiModelProperty(value="返回信息",name="msg",example="")
    private String msg;

    @ApiModelProperty(value="返回业务数据",name="data",example="JSON")
    private T data;

    public RetResult<T> setCode(RetCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }

}

