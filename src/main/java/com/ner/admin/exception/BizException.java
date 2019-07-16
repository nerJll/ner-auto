package com.ner.admin.exception;

/**
 * Created by jll on 2018/1/24.
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code;

    public BizException() {
        this.code = 500;
    }

    public BizException(String msg, Throwable cause) {
        super(msg,cause);
        this.msg = msg;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public BizException(String msg, int code, Throwable cause) {
        super(msg,cause);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg) {
        this.code = 500;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
