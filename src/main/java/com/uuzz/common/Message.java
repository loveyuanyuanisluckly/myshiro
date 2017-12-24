package com.uuzz.common;

/**
 * @author zj
 * STATE 消息状态
 * RETURN 返回内容
 * createTime: 2017/12/17
 */
public class Message<STATE,RETURN> {

    public final STATE state;

    public final RETURN msg;

    public Message(STATE state,RETURN msg){
        this.state = state;
        this.msg = msg;
    }

    public STATE getState() {
        return state;
    }

    public RETURN getMsg() {
        return msg;
    }
}
