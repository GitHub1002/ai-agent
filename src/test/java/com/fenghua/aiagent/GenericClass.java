package com.fenghua.aiagent;

import lombok.Builder;

/**
 * @ClassName GenericClass
 * @Description
 * @Author Feng
 * @Date 2025/12/18
 **/
@Builder
public class GenericClass <T>{
        private T t;

        public GenericClass(T t) {
                this.t = t;
        }

        public T getT() {
                return t;
        }
}
