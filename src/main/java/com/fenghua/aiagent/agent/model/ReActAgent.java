package com.fenghua.aiagent.agent.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ReActAgent
 * @Description 模式的代理抽象类  ReAct (Reasoning and Acting)
 * @Author Feng
 * @Date 2025/12/14
 **/
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ReActAgent extends BaseAgent {
    /**
     * @Author Feng
     * @Description
     * @Date 2025/12/14
     * @Param []
     * @return boolean
     **/
    public abstract boolean think();

    /**
     * @Author Feng
     * @Description  执行决定的行动
     * @Date 2025/12/14
     * @Param []
     * @return 行动执行的结果
     **/
    public abstract String act();

    /**
     * @Author Feng
     * @Description  执行一步, 先思考, 再行动
     * @Date 2025/12/14
     * @Param []
     * @return 步骤执行的结果
     **/
    @Override
    public String step(){
        try {
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成-无需行动";
            }
            return act();
        } catch (Exception e) {
            e.printStackTrace();
            return "步骤执行失败" + e.getMessage();
        }
    }
}
