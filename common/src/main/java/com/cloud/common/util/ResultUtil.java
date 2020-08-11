package com.cloud.common.util;

import com.cloud.common.base.vo.Result;

public class ResultUtil<T> {

    private Result<T> result;

    //未认证
    public static final int UNAUTHORIZED = 401;
    //未授权
    public static final int FORBIDDEN = 403;
    //参数校验失败
    public static final int VALIDATE_FAILED = 404;

    public ResultUtil(){
        result = new Result<>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(200);
    }

    public Result<T> setData(T t){
        this.result.setData(t);
        this.result.setCode(200);
        return this.result;
    }

    public Result<T> setSuccessMsg(String msg){
        this.result.setSuccess(true);
        this.result.setMessage(msg);
        this.result.setCode(200);
        this.result.setData(null);
        return this.result;
    }

    public Result<T> setData(T t, String msg){
        this.result.setData(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        return this.result;
    }

    public Result<T> setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(500);
        return this.result;
    }

    public Result<T> setErrorMsg(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }

    /**
     * 参数验证失败使用
     *
     * @param msg 错误信息
     */
    public Result<T> validateFailed(String msg) {
        this.result.setSuccess(false);
        this.result.setCode(VALIDATE_FAILED);
        this.result.setMessage(msg);
        return this.result;
    }

    /**
     * 未登录时使用
     *
     * @param msg 错误信息
     */
    public Result<T> unauthorized(String msg) {
        this.result.setSuccess(false);
        this.result.setCode(UNAUTHORIZED);
        this.result.setMessage("暂未登录或token已经过期");
        return this.result;
    }

    /**
     * 未授权时使用
     *
     * @param msg 错误信息
     */
    public Result<T> forbidden(String msg) {
        this.result.setSuccess(false);
        this.result.setCode(FORBIDDEN);
        this.result.setMessage("没有相关权限");
        return this.result;
    }

    public static <T> Result<T> data(T t){
        return new ResultUtil<T>().setData(t);
    }

    public static <T> Result<T> data(T t, String msg){
        return new ResultUtil<T>().setData(t, msg);
    }

    public static <T> Result<T> success(String msg){
        return new ResultUtil<T>().setSuccessMsg(msg);
    }

    public static <T> Result<T> error(String msg){
        return new ResultUtil<T>().setErrorMsg(msg);
    }

    public static <T> Result<T> error(Integer code, String msg){
        return new ResultUtil<T>().setErrorMsg(code, msg);
    }


}
