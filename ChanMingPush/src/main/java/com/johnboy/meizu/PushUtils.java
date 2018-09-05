package com.johnboy.meizu;

import com.alibaba.fastjson.JSONObject;
import com.meizu.ups.sdk.constant.PushType;
import com.meizu.ups.sdk.server.IFlymeUpsPush;
import com.meizu.ups.sdk.server.constant.ResultPack;
import com.meizu.ups.sdk.server.model.push.PushResult;
import com.meizu.ups.sdk.server.model.push.UnVarnishedMessage;
import com.meizu.ups.sdk.server.model.push.VarnishedMessage;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PushUtils {
    private String APP_SECRET_KEY="aa";
    //非通透
    @Test
    public void testVarnishedMessagePush() throws IOException {
        IFlymeUpsPush push =new IFlymeUpsPush(APP_SECRET_KEY);
        VarnishedMessage message =new VarnishedMessage.Builder().appId(222L)
                .title("Java SDK 推送标题").content("消息内容").validTime(5*24)
                .build();

        List<String> pushIds =new ArrayList<String>();
        pushIds.add("1");
        pushIds.add("2");
        ResultPack<PushResult> result =push.pushMessage(message,pushIds);
        if(result.isSucceed()){
            PushResult pushResult = result.value();
            String msgId = pushResult.getMsgId();//推送消息ID，用于推送流程明细排查
            Map<String, List<String>> targetResultMap =
                    pushResult.getRespTarget();//推送结果，全部推送成功，则map为empty
            System.out.println("push result:" + pushResult);
            if (targetResultMap != null && !targetResultMap.isEmpty()) {
                System.err.println("push fail token:" + targetResultMap);
            } else {
                // 调用推送接口服务异常 eg: appId、appKey非法、推送消息非法.....
                // result.code(); //服务异常码
                // result.comment();//服务异常描述
                System.out.println(String.format("pushMessage error code:%s comment:%s", result.code(), result.comment()));
            }
        }

    }

    @Test
    public void testUnVarnishedMessagePush() throws IOException {
        IFlymeUpsPush push =new IFlymeUpsPush(APP_SECRET_KEY);
        UnVarnishedMessage message = new UnVarnishedMessage.Builder().appId(222L)
                .title("title")
                .content("content")
                .build();

        List<String> pushIds =new ArrayList<String>();
        pushIds.add("1");
        pushIds.add("2");
        ResultPack<PushResult> result =push.pushMessage(message,pushIds);
        if(result.isSucceed()){
            PushResult pushResult = result.value();
            String msgId = pushResult.getMsgId();//推送消息ID，用于推送流程明细排查
            Map<String, List<String>> targetResultMap =
                    pushResult.getRespTarget();//推送结果，全部推送成功，则map为empty
            System.out.println("push result:" + pushResult);
            if (targetResultMap != null && !targetResultMap.isEmpty()) {
                System.err.println("push fail token:" + targetResultMap);
            } else {
                // 调用推送接口服务异常 eg: appId、appKey非法、推送消息非法.....
                // result.code(); //服务异常码
                // result.comment();//服务异常描述
                System.out.println(String.format("pushMessage error code:%s comment:%s", result.code(), result.comment()));
            }
        }
    }
    @Test
    public void testPushToApp() throws IOException {
        IFlymeUpsPush push =new IFlymeUpsPush(APP_SECRET_KEY);
        JSONObject param =new JSONObject();
        param.put("key1","value1");
        param.put("key2","value2");
        param.put("key3","value3");

        VarnishedMessage message =new VarnishedMessage.Builder().appId(222L)
                .title("Java SDK 推送标题").content("消息内容").validTime(5*24)
                .build();

        ResultPack<Long> result =push.pushToApp(PushType.STATUSBAR,message);

    }
}
