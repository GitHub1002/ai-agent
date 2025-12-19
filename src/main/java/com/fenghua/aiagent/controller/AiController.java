package com.fenghua.aiagent.controller;

import com.fenghua.aiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * @ClassName AiController
 * @Description
 * @Author Feng
 * @Date 2025/12/18
 **/
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    /**
     * @Author Feng
     * @Description  同步对话
     * @Date 2025/12/18
     * @Param [message, chatId]
     * @return java.lang.String
     **/
    @GetMapping("/love_app/chat/sync")
    public String doChat(String message, String chatId){
        String content = loveApp.doChat(message, chatId);
        return content;
    }

    /**
     * @Author Feng
     * @Description  流式对话
     * @Date 2025/12/18
     * @Param [message, chatId]
     * @return reactor.core.publisher.Flux<java.lang.String>
     **/
    @GetMapping("/love_app/chat/stream")
    public Flux<String> doChatWithStream(String message, String chatId){
        Flux<String> content = loveApp.doChatWithStream(message, chatId);
        return content;
    }

    /**
     * @Author Feng
     * @Description  流式对话事件流
     * @Date 2025/12/18
     * @Param [message, chatId]
     * @return reactor.core.publisher.Flux<org.springframework.http.codec.ServerSentEvent<java.lang.String>>
     **/
    @GetMapping("/love_app/chat/sse")
    public Flux<ServerSentEvent<String>> doChatWithStreamEmitter(String message, String chatId){
        return loveApp.doChatWithStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    @GetMapping("/love_app/chat/sse/emitter")
    public SseEmitter doChatWithLoveAppSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter emitter = new SseEmitter(180000L); // 3分钟超时
        // 获取 Flux 数据流并直接订阅
        loveApp.doChatWithStream(message, chatId)
                .subscribe(
                        // 处理每条消息
                        chunk -> {
                            try {
                                emitter.send(chunk);
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        // 处理错误
                        emitter::completeWithError,
                        // 处理完成
                        emitter::complete
                );
        // 返回emitter
        return emitter;
    }

}
