package com.fenghua.aiagent.tools;

import com.fenghua.aiagent.agent.model.TerminateTool;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 集中的工具注册类
 */
@Configuration
public class ToolRegistration {

    @Bean
    public ToolCallback[] allTools() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        TerminateTool terminateTool = new TerminateTool();

        return ToolCallbacks.from(
                terminateTool,
                webScrapingTool
        );
    }

}
