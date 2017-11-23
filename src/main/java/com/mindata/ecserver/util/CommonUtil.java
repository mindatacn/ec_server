package com.mindata.ecserver.util;


import com.mindata.ecserver.global.constant.Constant;
import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.io.file.FileReader;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class CommonUtil {
    public static Date getNow() {
        return new Date();
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取加密后的密码
     *
     * @return 密码
     */
    public static String password(String oldPass) {
        Object simpleHash = new SimpleHash("MD5", oldPass, Constant.SALT, 2);
        return simpleHash.toString();
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的开始
     */
    public static Date beginOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        try {
            return dateFormat.parse(date + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的结束
     */
    public static Date endOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        try {
            return dateFormat.parse(date + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将object转为long
     *
     * @param object
     *         object
     * @return long
     */
    public static Long parseObject(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        return 0L;
    }

    public static boolean isMobile(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static String secToTime(Integer time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    public static void main(String[] args) {
        //System.out.println(password("admin"));
        jisuan();
    }

    private static void jisuan() {
        FileReader fileReader = new FileReader("/Users/wuwf/Downloads/2.csv", "gbk");
        List<String> list = fileReader.readLines();
        list.remove(0);
        for (String line : list) {
            String[] array = line.split(",");
            String finish = array[4];
            //取电话次数
            int a = finish.indexOf("\"");
            int b = finish.indexOf("次");
            int cishu = Integer.valueOf(finish.substring(a + 1, b));

            int begin = finish.indexOf("：");
            int end = finish.indexOf("秒");
            String s = finish.substring(begin + 1, end);
            int userSeconds = getSeconds(s);
            DecimalFormat df = new DecimalFormat("######0.00");
            double scale = userSeconds * 100.0 / 3600;
            double noscale = (3600 - userSeconds) * 100.0 / 3600;
            String result = array[0].replace("\"", "") + "," +
                    "60分钟" + ",已打" + secToTime(userSeconds) + ",未打" + secToTime(3600 - userSeconds) + ",电话完成率"
                    + df.format(scale) + "%,未完成率" + df.format(noscale) + "%" + ",拜访" + array[9].replace("\"", "") +
                    "人,"
                    + "加上折算、累计次数" + (cishu + Integer.valueOf(array[9].replace("\"", "")) * 20) + ",还欠次数" + (60 -
                    (cishu + Integer.valueOf(array[9].replace("\"", "")) * 20));
            System.out.println(result);
        }
    }

    private static Integer getSeconds(String hourStr) {
        int count = 0;
        if (hourStr.contains("小时")) {
            Integer hour = Integer.valueOf(hourStr.substring(0, hourStr.indexOf("小时")));
            count += hour * 3600;
            hourStr = hourStr.replace(hour + "小时", "");
        }
        if (hourStr.contains("分")) {
            Integer hour = Integer.valueOf(hourStr.substring(0, hourStr.indexOf("分")));
            count += hour * 60;
            hourStr = hourStr.replace(hour + "分", "");
        }
        count += Integer.valueOf(hourStr);
        return count;
    }
}
