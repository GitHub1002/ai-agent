package com.fenghua.aiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * @ClassName MyLoggerAdvisor
 * @Description
 * @Author Feng
 * @Date 2025/12/5
 **/
@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    public AdvisedRequest before(AdvisedRequest advisedRequest) {
        log.info("AI before: {}", advisedRequest.userText());
        return advisedRequest;
    }

    public void observeAfter(AdvisedResponse advisedResponse) {
        log.info("AI response: {}", advisedResponse.response().getResult().getOutput().getText());
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        AdvisedRequest before = before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(before);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        Flux<AdvisedResponse> advisedResponse = chain.nextAroundStream(this.before(advisedRequest));
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponse, this::observeAfter);
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
