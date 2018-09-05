package com.johnboy.xiaomi;

import com.xiaomi.xmpush.server.*;

public class Main {
    public static void main(String[] args) {
        Constants.useSandbox();
    }

    private void sendMessage() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender("");
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName("packageName")
                .notifyType(1)     // 使用默认提示音提示
                .build();


        Result result = sender.send(message,"1", 3);
    }

    private Message buildMessage() throws Exception {
        String PACKAGENAME = "com.xiaomi.mipushdemo";
        String messagePayload = "This is a message";
        TargetedMessage targetedMessage = new TargetedMessage();
        Message message = new Message.Builder()
                .payload(messagePayload)
                .restrictedPackageName("package")
                .passThrough(1)  // 消息使用透传方式
                .notifyType(1)   // 使用默认提示音提示
                .build();
        return message;
    }

    private Message buildIOSMessage() throws Exception {
        String description =  "description";
        Message message = new Message.IOSBuilder()
                .description(description)
                .soundURL("default")    // 消息铃声
                .badge(1)               // 数字角标
                .category("action")     // 快速回复类别
                .extra("key", "value")  // 自定义键值对
                .build();
        return message;
    }
}
