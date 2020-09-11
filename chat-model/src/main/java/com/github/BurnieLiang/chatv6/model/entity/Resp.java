package com.github.BurnieLiang.chatv6.model.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author Boning Liang
 * @date 2020-09-11 15:13:43
 */
@Builder
@ToString
@AllArgsConstructor
public class Resp<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * @desc 成功标记
     */
    public final static Integer SUCCESS = 1;

    /**
     * @desc 失败标记
     */
    public final static Integer FAIL = 0;

    @Getter
    @Setter
    private int status = SUCCESS;

    @Getter
    @Setter
    private String msg = "success";

    @Getter
    @Setter
    private String action = "none";

    @Getter
    @Setter
    private T data;

    public Resp() {
        super();
    }

    public Resp(T data) {
        this.data = data;
    }

    public Resp(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public Resp(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Resp(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.status = FAIL;
    }

}
