package com.fenghua.aiagent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @ClassName GenericClassTest
 * @Description
 * @Author Feng
 * @Date 2025/12/18
 **/
public class GenericClassTest {
        @Test
        void testBuilder(){
            // 这里使用<String> 是为了指定泛型类型，避免编译器报错
            // 也可以不指定，但是编译器会警告，因为它无法确定泛型类型
            GenericClass<String> genericClass = GenericClass.<String>builder().t("test").build();
            assertEquals("test", genericClass.getT());
        }
}
