package com.fenghua.aiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

/**
 * @ClassName MyLoggerAdvisor
 * @Description
 * @Author Feng
 * @Date 2025/12/5
 **/
@Slf4j
public class MyLoggerAdvisor implements CallAdvisor, StreamAdvisor {

    public ChatClientRequest before(ChatClientRequest chatClientRequest) {
        log.info("AI before: {}", chatClientRequest.prompt());
        return chatClientRequest;
    }

    public void observeAfter(ChatClientResponse chatClientResponse) {
        log.info("AI response: {}", chatClientResponse.chatResponse().getResult().getOutput().getText());
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientRequest before = before(chatClientRequest);
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(before);
        this.observeAfter(chatClientResponse);
        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        Flux<ChatClientResponse> advisedResponse = streamAdvisorChain.nextStream(this.before(chatClientRequest));
        return (new ChatClientMessageAggregator()).aggregateChatClientResponse(advisedResponse, this::observeAfter);
        // 上面的代码等价于下面的代码
//        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponse, response -> this.observeAfter(response));
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
