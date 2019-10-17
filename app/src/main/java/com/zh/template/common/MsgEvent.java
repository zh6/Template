package com.zh.template.common;

/**
 * RxBus信息
 */
public class MsgEvent {
    private String msg;

    public MsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
