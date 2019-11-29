package com.jq.user.support;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/7/2
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_MM_HH_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static final Map<String, SimpleDateFormat> simpleDateformatMap = new HashMap();

    public DateUtil() {
    }

    public static String getYyyyMmDdHhMmSsSss(Date date) {
        return dateToString(date, FORMAT_FULL);
    }

    public static String dateToString(Date uDate, String pattern) {
        return getDateFormat(pattern).format(uDate);
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat = getDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String dateToYYYYMMDDString(Date date) {
        return getDateFormat("yyyy-MM-dd").format(date);
    }

    public static String dateToYMDStr(Date date) {
        return getDateFormat("yyyyMMdd").format(date);
    }

    public static Date strToDate(String dateString) {
        Date date = null;

        try {
            date = getDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException var3) {
            logger.error("转换日期格式发生错误", var3);
        }

        return date;
    }

    public static Date strToDate(String dateString, String pattern) {
        Date date = null;

        try {
            date = getDateFormat(pattern).parse(dateString);
        } catch (ParseException var4) {
            logger.error("转换日期格式发生错误", var4);
        }

        return date;
    }

    public static Date strToYYMMDDDate(String dateString) {
        Date date = null;

        try {
            date = getDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException var3) {
            logger.error("转换日期格式发生错误", var3);
        }

        return date;
    }

    public static Date strToYYMMDDDMMHHSSdate(String dateString) {
        Date date = null;

        try {
            DateFormat dateFormat = getDateFormat("yyyy-MM-dd HH:mm:ss");
            date = dateFormat.parse(dateString);
        } catch (ParseException var3) {
            logger.error("转换日期格式发生错误", var3);
        }

        return date;
    }

    public static long diffDays(Date startDate, Date endDate) {
        long days = 0L;
        long start = startDate.getTime();
        long end = endDate.getTime();
        days = (end - start) / 86400000L;
        return days;
    }

    public static Date dateAddMonth(Date date, int month) {
        return add(date, 2, month);
    }

    public static Date dateAddDay(Date date, int day) {
        return add(date, 6, day);
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (startTime != null && endTime != null && nowTime != null) {
            return nowTime.getTime() >= startTime.getTime() && nowTime.getTime() <= endTime.getTime();
        } else {
            return false;
        }
    }

    public static boolean isEffectiveDate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            long currentTime = (new Date()).getTime();
            return currentTime >= startDate.getTime() && currentTime <= endDate.getTime();
        } else {
            return false;
        }
    }

    public static Date dateAddYear(Date date, int year) {
        return add(date, 1, year);
    }

    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "过期";
        } else {
            long times = endDate.getTime() - startDate.getTime();
            if (times < -1L) {
                result.append("过期");
            } else {
                long temp = 86400000L;
                long d = times / temp;
                times %= temp;
                temp /= 24L;
                long m = times / temp;
                times %= temp;
                temp /= 60L;
                long s = times / temp;
                result.append(d);
                result.append("天");
                result.append(m);
                result.append("小时");
                result.append(s);
                result.append("分");
            }

            return result.toString();
        }
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    public static long getTimeCur(String format, String date) throws ParseException {
        SimpleDateFormat sf = getDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    public static long getTimeCur(String format, Date date) throws ParseException {
        SimpleDateFormat sf = getDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = getDateFormat("yyyy:MM:dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    public static String formatDateToStr(String format, Date date) throws ParseException {
        SimpleDateFormat sf = getDateFormat(format);
        return sf.format(date);
    }

    public static String showDayStr(Long time) {
        if (time <= 0L) {
            return "";
        } else {
            StringBuffer result = new StringBuffer();
            int days = (int)(time / 86400000L);
            int hours = (int)(time / 1000L / 60L / 60L % 60L);
            int minutes = (int)(time / 1000L / 60L % 60L);
            int second = (int)(time / 1000L % 60L);
            if (days != 0) {
                result.append(days).append("天");
            }

            if (hours != 0) {
                result.append(hours).append("小时");
            }

            if (minutes != 0) {
                result.append(minutes).append("分");
            }

            if (second != 0) {
                result.append(second).append("秒");
            }

            return result.toString();
        }
    }

    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList();
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        boolean bContinue = true;

        while(bContinue) {
            cal.add(5, 1);
            if (!endDate.after(cal.getTime())) {
                break;
            }

            lDate.add(cal.getTime());
        }

        lDate.add(endDate);
        return lDate;
    }

    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearLast(currentYear);
    }

    public static Date getCurrDate() {
        String date = dateToYYYYMMDDString(new Date());
        return strToYYMMDDDate(date);
    }

    public static String getCurrDateStr() {
        return dateToYYYYMMDDString(new Date());
    }

    public static int compareTo(Date beginDate, Date endDate) {
        int result = -2;
        if (beginDate != null && endDate != null) {
            long d = beginDate.getTime() - endDate.getTime();
            if (d > 0L) {
                result = 1;
            } else if (d == 0L) {
                result = 0;
            } else if (d < 0L) {
                result = -1;
            }

            return result;
        } else {
            return result;
        }
    }

    public static int compareTo(String fromDate, String toDate) {
        if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            Date beginDate = fromDate.contains(":") ? strToYYMMDDDMMHHSSdate(fromDate) : strToYYMMDDDate(fromDate);
            Date endDate = toDate.contains(":") ? strToYYMMDDDMMHHSSdate(toDate) : strToYYMMDDDate(toDate);
            return compareTo(beginDate, endDate);
        } else {
            return -2;
        }
    }

    public static Date toDate(Date date, String format) {
        if (date != null && !StringUtils.isBlank(format)) {
            try {
                String dateStr = formatDateToStr(format, date);
                return getDateFormat(format).parse(dateStr);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Double compareDateToHour(Date date1, Date date2) {
        Double y = (double)(date1.getTime() - date2.getTime()) / 3600000.0D;
        return y;
    }

    public static String getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11) + hour);
        return getDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String dateStrAddDay(String dateStr, int day) {
        if (dateStr != null && dateStr.length() != 0) {
            Date theDateAddDay = dateAddDay(strToYYMMDDDate(dateStr), day);
            String theDateStrAddDay = dateToYYYYMMDDString(theDateAddDay);
            return theDateStrAddDay;
        } else {
            return "";
        }
    }

    public static int dateCut(Date date, int dar) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        return dar == 5 ? cal.get(dar) + 1 : cal.get(dar);
    }

    public static Date dateResetTime(Date date, int hour, int minute, int second) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (hour >= 0 && hour <= 23) {
                cal.set(11, hour);
            }

            if (minute >= 0 && minute <= 59) {
                cal.set(12, minute);
            }

            if (second >= 0 && second <= 59) {
                cal.set(13, second);
            }

            return cal.getTime();
        }
    }

    public static long differenceTime(Date startDate, Date endDate) {
        long diffTime = 0L;
        if (startDate != null && endDate != null) {
            diffTime = endDate.getTime() - startDate.getTime();
        }

        return diffTime;
    }

    public static Date getMonthFirstDay(Date date) {
        Date newDate = new Date(date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        newDate.setTime(calendar.getTimeInMillis());
        return newDate;
    }

    public static Date getMonthLastDay(Date date) {
        Date newDate = new Date(date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        int maxDay = calendar.getActualMaximum(5);
        calendar.set(5, maxDay);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        newDate.setTime(calendar.getTimeInMillis());
        return newDate;
    }

    public static boolean isValidDate(String date, String format) {
        try {
            SimpleDateFormat sdf = getDateFormat(format);
            Date d = sdf.parse(date);
            return date.equals(sdf.format(d));
        } catch (Exception var4) {
            return false;
        }
    }

    public static Date offsetSecond(Date date, long seconds) {
        long time = date.getTime();
        time += seconds * 1000L;
        return new Date(time);
    }

    public static Date offsetMinute(Date date, long minutes) {
        return offsetSecond(date, 60L * minutes);
    }

    public static Date offsetHour(Date date, long hours) {
        return offsetMinute(date, 60L * hours);
    }

    public static Date offsetDay(Date date, int days) {
        return offsetHour(date, (long)(24 * days));
    }

    public static Date offsetWeek(Date date, int weeks) {
        return offsetDay(date, 7 * weeks);
    }

    public static void main(String[] args) throws Exception {
    }

    public static Date getYesterday(Date date) {
        Date newDate = new Date(date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.add(5, -1);
        newDate.setTime(calendar.getTimeInMillis());
        return newDate;
    }

    public static String getMonthDay(Date date, String day) {
        SimpleDateFormat df = getDateFormat("yyyy-MM-dd");
        String format = df.format(date);
        String substring = format.substring(0, 8);
        return substring + day;
    }

    public static String getPerMonthDay(String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.add(5, -1);
        Date preDate = calendar.getTime();
        return getMonthDay(preDate, day);
    }

    public static String getPerMonthLastDay(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.add(5, -1);
        return dateToString(calendar.getTime(), format);
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    public static Date getThisMonday() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        int dayWeek = cal.get(7);
        if (1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(2);
        int day = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getLastMonday() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.add(3, -1);
        int dayWeek = cal.get(7);
        if (1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(2);
        int day = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getLastSunday() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.add(3, -1);
        int dayWeek = cal.get(7);
        if (1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(2);
        int day = cal.get(7);
        cal.add(5, day - cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    private static SimpleDateFormat getDateFormat(String format) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)simpleDateformatMap.get(format);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(format);
            simpleDateformatMap.put(format, simpleDateFormat);
        }

        return simpleDateFormat;
    }

    /**
     *
     * 获取当前周是本月第几周（以周一在本月内计算）
     *
     * @return
     * @author jason
     * @date 2018年8月14日
     */
    public static int getThisWeekByMonth(Date date){
        Calendar cal = Calendar.getInstance();
        // 获取本周周一的日期，重置开始时间
        cal.setTime(date);
        // 获取本周周一是当月第几周
        return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获取本周日日期
     *
     * @return
     * @author jason
     * @date 2018年8月14日
     */
    public static Date getLastThisSunday(){
        // 周一加6天
        return offsetDay(getThisMonday(), +6);
    }

    static {
        simpleDateformatMap.put("HH:mm:ss", new SimpleDateFormat("HH:mm:ss"));
        simpleDateformatMap.put("yyyyMM", new SimpleDateFormat("yyyyMM"));
        simpleDateformatMap.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));
        simpleDateformatMap.put("yyyy-MM-dd HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        simpleDateformatMap.put("HHmmss", new SimpleDateFormat("HHmmss"));
        simpleDateformatMap.put("yyyyMMdd", new SimpleDateFormat("yyyyMMdd"));
        simpleDateformatMap.put("yyyyMMddHHmmss", new SimpleDateFormat("yyyyMMddHHmmss"));
        simpleDateformatMap.put(FORMAT_FULL, new SimpleDateFormat(FORMAT_FULL));
    }

    /**
     * 根据当前时间获取 接下来一周 的时间字符串  格式 yyyyMMdd
     * @param firstWeekOfMonth
     */
    public static List<String>  getWholeWeekYYYYMMDDStr(Date firstWeekOfMonth) {
        List<String> week=new ArrayList<>();
        for (int i=0; i<7 ;i++) {
            Date date1 = dateAddDay(firstWeekOfMonth, i);
            week.add(DateUtil.dateToString(date1,"yyyyMMdd"));
        }
        return week;
    }

    /**
     * 获取指定月份每周的周一及周日的日期（周包含跨月）
     *
     * @param monthDay
     * @return
     */
    public static List<String> getWeekByMonth(String monthDay) {
        // Java8  LocalDate
        LocalDate date = LocalDate.parse(monthDay);

        // 该月第一天
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        // 该月最后一天
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        // 该月的第一个周一
        LocalDate start = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        List<String> list = new ArrayList<>();

        while (start.isBefore(lastDay)) {

            StringBuilder strbur = new StringBuilder();
            strbur.append(start.toString());

            LocalDate temp = start.plusDays(6);
            strbur.append("/").append(temp.toString());

            list.add(strbur.toString());
            start = start.plusWeeks(1);
        }

        return list;
    }
}

