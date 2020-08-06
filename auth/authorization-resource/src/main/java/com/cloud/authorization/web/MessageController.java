package com.cloud.authorization.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @GetMapping("/messages")
    public String[] getMessages() {
        String[] messages = new String[]{"message1","message2","message3"};
        return messages;
    }
}
