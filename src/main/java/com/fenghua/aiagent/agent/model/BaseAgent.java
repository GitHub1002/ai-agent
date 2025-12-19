package com.fenghua.aiagent.agent.model;

import com.alibaba.cloud.ai.agent.Agent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.util.StringUtil;
import org.apache.catalina.User;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseAgent
 * @Description 抽象基础代理类, 用于管理代理状态和执行流程。
 * 提供状态转换、内存管理和基于步骤的执行循环的基础功能。
 * 子类必须实现step方法
 * @Author Feng
 * @Date 2025/12/14
 **/
@Slf4j
@Data
public abstract class BaseAgent {

    private String name;

    // 提示词
    private String systemPrompt;
    private String nextStepPrompt;

    // 状态
    private AgentState state = AgentState.IDLE;

    // 执行控制
    private int maxSteps = 10;
    private int currentStep = 0;

    // LLM
    private ChatClient chatClient;

    // Memory(需要自主维护会话上下文)
    private List<Message> messageList = new ArrayList<Message>();

     /**
     * @Author Feng
     * @Description 运行代理, 执行多个步骤直到完成或达到最大步数
     * @Date 2025/12/14
     * @Param userPrompt 初始用户提示
     * @return 代理执行结果
     **/
    public String run(String userPrompt){
        // 检查状态是否为空闲
        if (this.state != AgentState.IDLE) {
            throw new IllegalStateException("Agent is not idle");
        }
        // 检查用户提示是否为空
        if (StringUtil.isEmpty(userPrompt)) {
            throw new IllegalArgumentException("User prompt cannot be empty");
        }
        // 设置状态为运行中
        this.state = AgentState.RUNNING;
        // 记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        // 保存结果列表
        List<String> results = new ArrayList<String>();

        try {
            for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                int stepNumber = i + 1;
                currentStep = stepNumber;
                log.info("Executing step " + stepNumber + "/" + maxSteps);
                // 单步执行
                String stepResult = step();
                String result = "Step" + stepNumber + ":" + stepResult;
                results.add(result);
            }
            if (currentStep >= maxSteps) {
                state = AgentState.FINISHED;
                results.add("Terminated: Reached max steps (" + maxSteps + ")");
            }

            return String.join("\n", results);
        } catch (Exception e) {
            state = AgentState.ERROR;
            log.error("Error executing agent", e);
            return "执行错误" + e.getMessage();
        } finally {
            // 清理资源
            cleanup();
        }
    }

    /**
     * @Author Feng
     * @Description 执行一步, 子类必须实现
     * @Date 2025/12/14
     * @Param
     * @return
     **/
    public abstract String step();

     /**
     * @Author Feng
     * @Description 清理资源
     * @Date 2025/12/14
     * @Param
     * @return
     **/
    protected void cleanup(){
        // 子类可以重写此方法来清理资源
    }
}
