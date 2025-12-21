# ai-agent智能体项目

## 项目介绍

ai-agent是一个基于Spring Boot和Spring AI构建的智能代理系统，专注于提供恋爱相关的智能问答服务。该项目利用大语言模型(LLM)和检索增强生成(RAG)技术，结合向量存储和聊天记忆功能，为用户提供个性化的恋爱咨询和建议。系统支持多种AI模型接入，包括阿里云DashScope、Ollama等，并具备工具调用能力，能够根据用户需求动态扩展功能。

## 技术栈

- **核心框架**: Spring Boot 3.4.4, Java 21
- **AI框架**: Spring AI Alibaba, DashScope SDK 2.19.1
- **模型支持**: DashScope (Qwen系列), Ollama, LangChain4J
- **向量存储**: PgVector Store
- **工具链**:
    - Hutool 5.8.37 (Java工具库)
    - Lombok 1.18.36 (简化Java代码)
    - Kryo 5.6.2 (高性能序列化)
    - Jsoup 1.19.1 (HTML解析)
- **文档处理**: Markdown Document Reader
- **API文档**: SpringDoc OpenAPI, Knife4j
- **构建工具**: Maven

## 项目架构

项目采用分层架构设计，主要包括以下模块：

1. **应用入口层**
    - `AiAgentApplication`: Spring Boot主应用类，已排除DataSource自动配置

2. **控制器层**
    - `AiController`: 提供AI对话接口，支持流式响应
    - `HealthController`: 应用健康检查接口

3. **核心业务逻辑层**
    - `LoveApp`: 恋爱应用核心类，整合聊天记忆、向量检索等功能

4. **智能代理层**
    - `BaseAgent`: 基础代理类
    - `ToolCallAgent`: 工具调用代理
    - `ReActAgent`: ReAct推理代理
    - `FengManus`: 自定义代理实现

5. **工具层**
    - `WebScrapingTool`: 网页抓取工具
    - `TerminateTool`: 终止工具
    - 各类自定义工具实现

6. **数据访问层**
    - `FileBasedChatMemory`: 基于文件的聊天记忆实现
    - `LoveAppVectorStoreConfig`: 向量存储配置
    - `LoveAppDocumentLoader`: 文档加载器

7. **配置层**
    - `CorsConfig`: 跨域配置
    - 多环境配置文件(application.yml, application-local.yml)

8. **AI模型接入层**
    - 集成DashScope、Ollama等多种模型
    - 支持ChatClient和底层ChatModel调用

9. **工具链支持层**
    - `MyLoggerAdvisor`: 日志顾问
    - MCP Client/Server支持

## 功能介绍

1. **智能对话**
    - 基于大语言模型的自然语言对话
    - 支持流式响应和普通响应模式
    - 集成聊天记忆，保持上下文连贯性

2. **恋爱专业问答**
    - 内置恋爱相关知识库(document目录下)
    - 利用RAG技术提供精准答案
    - 支持个性化建议生成

3. **工具调用**
    - 动态工具注册与发现
    - 支持网页内容抓取等扩展功能
    - 工具执行结果反馈至对话流程

4. **多模型支持**
    - 可切换不同AI服务商和模型类型
    - 统一接口封装，易于扩展新模型

5. **向量检索增强**
    - 将文档知识转化为向量存储
    - 对话时实时检索相关知识点
    - 提高回答准确性和专业性

6. **聊天记忆管理**
    - 文件持久化聊天记录
    - 支持会话历史回溯
    - 保证长期对话体验

## 运行项目

1. 确保已安装Java 21和Maven
2. 在`src/main/resources/application-local.yml`中配置DashScope API Key:
   ```yaml
   spring:
     ai:
       dashscope:
         api-key: sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx