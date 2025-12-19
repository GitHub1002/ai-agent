package com.fenghua.imagesearchmcpserver.tool;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageSearchToolTest {

    @Resource
    private ImageSearchTool imageSearchTool;

    @Test
    void imageSearchTest(){
        String girls = imageSearchTool.searchImage("girls");
        Assertions.assertNotNull(girls);
    }
}