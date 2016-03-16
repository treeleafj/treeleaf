package org.treeleaf.wechat.pay;

import org.junit.Test;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.wechat.pay.entity.Redpack;
import org.treeleaf.wechat.pay.entity.RedpackResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by leaf on 2016/3/17 0017.
 */
public class WechatRedpackTest {

    @Test
    public void testSend() throws Exception {

        WechatRedpack wechatRedpack = new WechatRedpack();
        wechatRedpack.setAppid("wxddb194fb3f28d83f");
        wechatRedpack.setKey("a123a123a123a123a123a123a123a123");
        wechatRedpack.setMerchantNo("1262649701");
        wechatRedpack.setCertPath("F:/java/cert/apiclient_cert.p12");

        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String billNo = "RB" + format.format(new Date()) + new Random().nextInt(10);

        Redpack redpack = new Redpack();
        redpack.setMch_billno(billNo);
        redpack.setSend_name("肆意云购");
        redpack.setRe_openid("oJ8JOuByQwrlCeghbJ07Y9FidkQk");
        redpack.setTotal_amount("100");
        redpack.setTotal_num("1");
        redpack.setWishing(" 肆意梦想，一元云购！");
        redpack.setClient_ip("127.0.0.1");
        redpack.setAct_name("新用户体验活动");
        redpack.setRemark("叫上朋友一起来体验领红包吧！");

        RedpackResult result = wechatRedpack.send(redpack);
        System.out.println(Jsoner.toJson(result));
    }
}