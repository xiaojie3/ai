package com.example.edu.aiagentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    @GetMapping
    public String chat(String userText) {
        List<Message> messages = new ArrayList<>();
        String systemText = """
  从以下选项识别用户意图：
  1. 查询学生课表
  2. 查询学生成绩
  3. 查询选课时间
  """;
        messages.add(new SystemMessage(systemText));
        messages.add(new UserMessage(userText));
        return chatClient.prompt(new Prompt(messages)).call().content();
    }

}
