package com.fenghua.aiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @ClassName LoveAppTest
 * @Description
 * @Author Feng
 * @Date 2025/12/4
 **/
@SpringBootTest
public class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChatTest(){
        String userMessage = "我单身了一个月，想找个对象";
        String response = loveApp.doChat(userMessage, "123");
        System.out.println(response);
        userMessage = "我和对象吵架了，想解决";
        response = loveApp.doChat(userMessage, "123");
        System.out.println(response);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "我心认识了一个女生，想和她在一起，我应该从哪开始";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRagTest() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "我心认识了一个女生，想和她在一起，我应该从哪开始";
        String response = loveApp.doChatWithRag(message, chatId);
        System.out.println(response);
    }

    @Test
    void doChatWithMcpTest() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "找一些帅哥的图片";
        String response = loveApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(response);
//        System.out.println(response);
    }

}
