package com.gofirst.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 	获得随机订单号的工具
 *
 */
public class RandomUtil {
	  /**
	   	*	 获取YYYY-MM-DD HH:mm:ss格式
	   	*/
    public static String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdfTime.format(new Date());
    }
    /**
     * 	随机生成六位数验证码
     */
    public static int getRandomNum(){
        Random r = new Random();
        return r.nextInt(900000)+100000;//(int)(Math.random()*999999)
    }
}
