package com.gofirst.model;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {

    public CommonResult() {
    }

    public CommonResult(T t) {
        this(ResponseCode.success.code(), null, t);
    }

    public CommonResult(int code, T t) {
        this(code, null, t);
    }

    public CommonResult(int code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
    }

    /*错误码*/
    private int code = ResponseCode.success.code();
    /*提示信息*/
    private String msg = "";
    /*具体的内容*/
    private T data = null;

    public CommonResult success() {
        return this;
    }

    public CommonResult success(T t) {
        this.msg = "请求成功";
        this.data = t;
        return this;
    }

    public CommonResult success(int code, T t) {
        this.code = code;
        this.msg = "请求成功";
        this.data = t;
        return this;
    }

    public CommonResult success(int code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
        return this;
    }

    public CommonResult failed() {
        this.code = ResponseCode.error.code();
        this.msg = "请求成功,未查询到数据";
        return this;
    }

    public CommonResult failed(int code, T t) {
        this.code = code;
        this.msg = "请求成功";
        return this;
    }

    public CommonResult failed(int code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
        return this;
    }

    public CommonResult failed(String msg) {
        this.code = ResponseCode.error.code();
        this.msg = msg;
        return this;
    }

    public CommonResult saveSuccess(int num) {
        this.msg = String.format("成功保存%s条数据", num);
        return this;
    }

    public CommonResult editSuccess(int num) {
        this.msg = String.format("成功修改%s条数据", num);
        return this;
    }

    public CommonResult deleteSuccess(int num) {
        this.msg = String.format("成功删除%s条数据", num);
        return this;
    }

    public static CommonResult error(String msg) {
        return new CommonResult(ResponseCode.error.code(), msg, null);
    }

    public static CommonResult build(ResponseCode responseCode) {
        return new CommonResult(responseCode.code(), responseCode.desc(), null);
    }

    public CommonResult buildByResponseCode(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
        return this;
    }

    /**
     * 异常类返回结果处理
     *
     * @param msg
     * @param exception
     * @return
     */
    public static CommonResult error(String msg, String exception) {
        return new CommonResult(ResponseCode.error.code(), msg, exception);
    }

    public static CommonResult error(Integer code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public static CommonResult errorFor401() {
        return new CommonResult(ResponseCode.unauthorized.code(), ResponseCode.unauthorized.desc(), null);
    }

    public static CommonResult errorFor402() {
        return new CommonResult(ResponseCode.invalideTokenFormat.code(), ResponseCode.invalideTokenFormat.desc(), null);
    }

    public static CommonResult errorFor412() {
        return new CommonResult(ResponseCode.invalideToken.code(), ResponseCode.invalideToken.desc(), null);
    }

    public static CommonResult errorFor413() {
        return new CommonResult(ResponseCode.nullToken.code(), ResponseCode.nullToken.desc(), null);
    }

    public static CommonResult errorFor403() {
        return new CommonResult(ResponseCode.permissionDeny.code(), ResponseCode.permissionDeny.desc(), null);
    }

    public static CommonResult errorFor404() {
        return new CommonResult(ResponseCode.invalideParam.code(), ResponseCode.invalideParam.desc(), null);
    }

    public static CommonResult errorFor409(String msg) {
        return new CommonResult(ResponseCode.pushSuccess.code(), ResponseCode.pushSuccess.desc(), msg);
    }

    public static CommonResult errorFor410(String msg) {
        return new CommonResult(ResponseCode.pushFailedNoUser.code(), ResponseCode.pushFailedNoUser.desc(), msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
