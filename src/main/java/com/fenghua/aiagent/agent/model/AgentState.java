package com.fenghua.aiagent.agent.model;

/**
 * @Author Feng
 * @Description 代理执行状态的枚举类
 * @Date 2025/12/14
 * @Param
 * @return
 **/
public enum AgentState {

    /**
     * 空闲状态
     */
    IDLE,

    /**
     * 运行中状态
     */
    RUNNING,

    /**
     * 已完成状态
     */
    FINISHED,

    /**
     * 错误状态
     */
    ERROR

}

