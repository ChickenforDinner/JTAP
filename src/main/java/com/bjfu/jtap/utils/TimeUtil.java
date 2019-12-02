package com.bjfu.jtap.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  14:55
 */
public class TimeUtil {

    /**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    public static void main(String[] args) throws ParseException {
        Date nowTime = new Date();

        String start = "2019-05-03 15:00:00";
        String end = "2019-05-07 15:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(start);
        Date endTime = sdf.parse(end);

        System.out.println(belongCalendar(nowTime,startTime,endTime));


    }

}
