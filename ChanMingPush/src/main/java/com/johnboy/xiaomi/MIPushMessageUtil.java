package com.johnboy.xiaomi;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

import java.util.List;

public class MIPushMessageUtil {
    /**
     *
     * @param title 设置在通知栏展示的通知的标题, 不允许全是空白字符, 长度小于50, 一个中英文字符均计算为1(通知栏消息必填)
     * @param description 设置在通知栏展示的通知描述, 不允许全是空白字符, 长度小于128, 一个中英文字符均计算为1(通知栏消息必填)
     * @param messagePayload 设置要发送的消息内容payload, 不允许全是空白字符, 长度小于4K, 一个中英文字符均计算为1(透传消息回传给APP, 为必填字段, 非透传消息可选)
     * @param passThrough  设置消息是否通过透传的方式至App, 1表示透传消息, 0表示通知栏消息(默认是通知栏消息)
     * @param notifyType 设置通知类型, type类型(-1, 1-使用默认提示音提示, 2-使用默认震动提示, 3-使用默认led灯光提示)
     * @param timeToLive 可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: ms(服务器默认最长保留两周)
     * @return
     */
    private Message buildMessageForAndroid(String title, String description, String messagePayload,
                                           Integer passThrough, Integer notifyType,long timeToLive) {
        Message message;
        Message.Builder builder = new Message.Builder()
                .title(title)//标题（注意16字真言限制长度，这段画上重点考）
                .description(description)//描述（注意128限制长度，这段画上重点考，这个描述，我理解为副标题，而且在手机客户端呈现的也是标题+描述，内容不会自己显示出来，如果只是为了通知用户信息，我们可以将信息内容放在此处，显示效果比较明显。但是三个文字区域都不可空。需要补充文字方可使用）
                .payload(messagePayload)//内容（这个长度够你用了）
                .restrictedPackageName("")//APP包名 设置app的包名packageName, packageName必须和开发者网站上申请的结果一致
                .passThrough(passThrough)//是否透传
                .notifyType(notifyType)//设置震动，响铃等等
                .timeToLive(timeToLive);//可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: ms(服务器默认最长保留两周)
        message = builder.build();
        return message;
    }

    /**
     *
     * @param title
     * @param description 设置在通知栏展示的通知描述
     * @param messagePayload
     * @param timeToLive 可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: ms(服务器默认最长保留两周)
     * @return
     */
    private Message buldMessageForIOS(String title, String description, String messagePayload,long timeToLive){
        Message message ;
        Message.IOSBuilder builder = new Message.IOSBuilder()
                .title(title)
                .description(description)
                .timeToLive(timeToLive)
                .body(messagePayload);
        message = builder.build();
        return  message;
    }

    public Result sendMessageToRegId(String title, String description,
                                     String messagePayload, Integer passThrough, Integer notifyType, List<String> regIds, long timeToLive) {

        Constants.useOfficial();//这里要注意，这是正式-启动方式，支持IOS跟Android,Constants.useSandbox();这是测试-启动方式，不支持Android，尽量申请正式APP,利用正式环境测试
        Sender sender = new Sender("你需要传入你所申请appsecret");
        Message message = buildMessageForAndroid(title, description, messagePayload, passThrough, notifyType,timeToLive);
        Result result = null;
        try {
            result = sender.send(message, regIds, 3);
        } catch (Exception e) {
            //log
        }
        return result;
    }
}
