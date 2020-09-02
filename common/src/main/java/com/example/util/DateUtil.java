package com.example.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * @author shiyakun
 */
public class DateUtil {

    public static final String SQL_MONTH = "yyyy-MM";
    public static final String SQL_DATE = "yyyy-MM-dd";
    public static final String SQL_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SQL_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SS";


    public static final String DATE = "yyyy/MM/dd";
    public static final String TIMESTAMP = "yyyy/MM/dd HH:mm:ss.SS";
    public static final String TIMESTAMP_SHORT = "yyyy/MM/dd HH:mm";
    public static final String TIME = "HH:mm:ss";
    public static final String TIME_SHORT = "HH:mm";


    public static final String CHINE_SEDATE = "yyyy年MM月dd日";
    public static final String DATE_TIME = "yyyyMMddHHmmss";
    public static final String DATE_TIME_DETAIL = "yyyyMMddHHmmssSS";
    public static final String DATE_DAY = "yyyyMMdd";
    public static final String DATE_HOUR = "yyyyMMddHH";
    public static final String DATE_MONTH = "yyyyMM";


    public static final String OTHER_YEAR = "yyyy";
    public static final String OTHER_MONTH = "yyyy.MM";

    public static final String OTHER_DATE = "yyyy.MM.dd";
    public static final String OTHER_TIME = "yyyy.MM.dd HH:mm:ss";
    public static final String OTHER_TIMESTAMP = "yyyy.MM.dd HH:mm:ss.SS";

    /**
     * 防止被实例化
     */
    private DateUtil() {

    }

    /**
     * Date转LocalDateTime
     * 使用系统时区
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     * 使用系统时区
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * localDateTime转字符串
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String dateTimeToStr(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * date类型转成string
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(dateTimeFormatter);
    }


    /**
     * 将字符串日期解析为java.time.LocalDateTime
     *
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 将字符串日期解析为date
     *
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static Date strToDate(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse = LocalDateTime.parse(dateTimeStr, formatter);
        return localDateTimeToDate(parse);
    }


    /**
     * 开始日期，补齐" 00:00:00"
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getStartLocalDateTimeWithHMS(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 结束日期，补齐" 23:59:59"
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getEndLocalDateWithHMS(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 开始日期，补齐" 00:00:00"
     * 返回date类型
     *
     * @param localDateTime
     * @return Date
     */
    public static Date getStartDateWithHMS(LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return localDateTimeToDate(of);
    }

    /**
     * 结束日期，补齐" 23:59:59"
     * 返回date类型
     *
     * @param localDateTime
     * @return Date
     */
    public static Date getEndDateWithHMS(LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return localDateTimeToDate(of);
    }


    /**
     * 往后推几年
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterYears(LocalDateTime localDateTime, int count) {
        return localDateTime.plusYears(count);
    }

    /**
     * 往后推几月
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterMonths(LocalDateTime localDateTime, int count) {
        return localDateTime.plusMonths(count);
    }

    /**
     * 往后推几天
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterDays(LocalDateTime localDateTime, int count) {
        return localDateTime.plusDays(count);
    }

    /**
     * 推几个小时
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterHours(LocalDateTime localDateTime, int count) {
        return localDateTime.plusHours(count);
    }

    /**
     * 推几分钟
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterMinutes(LocalDateTime localDateTime, int count) {
        return localDateTime.plusMinutes(count);
    }

    /**
     * 推几秒
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getAfterSeconds(LocalDateTime localDateTime, int count) {
        return localDateTime.plusSeconds(count);
    }


    /**
     * 往前推几月
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getBeforeMonths(LocalDateTime localDateTime, int count) {
        return localDateTime.minusMonths(count);
    }

    /**
     * 往前推几周
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getBeforeWeeks(LocalDateTime localDateTime, int count) {
        return localDateTime.minusWeeks(count);
    }

    /**
     * 往前推几天
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getBeforeDays(LocalDateTime localDateTime, int count) {
        return localDateTime.minusDays(count);
    }

    /**
     * 前推几个小时
     *
     * @param localDateTime
     * @param count
     * @return
     */
    public static LocalDateTime getBeforeHours(LocalDateTime localDateTime, int count) {
        return localDateTime.minusHours(count);
    }

    /**
     * 获得当前年的第一天
     *
     * @param
     * @return
     */
    public static LocalDateTime getYearFirstDay(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获得当前年的最后一天
     *
     * @param
     * @return
     */
    public static LocalDateTime getYearLastDay(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfYear());
    }


    /**
     * 获得当前月的第一天
     *
     * @param
     * @return
     */
    public static LocalDateTime getMonthFirstDay(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获得当前月的最后一天
     *
     * @param
     * @return
     */
    public static LocalDateTime getMonthLastDay(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获得当前星期的第一天
     *
     * @param localDateTime
     * @param locale        默认Locale.CHINA 周日为一周的第一天
     * @return
     */
    public static LocalDateTime getWeekFirstDay(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.with(WeekFields.of(locale == null ? Locale.CHINA : locale).dayOfWeek(), 1);
    }

    /**
     * 获得当前星期的最后一天
     *
     * @param localDateTime
     * @param locale        默认默认Locale.CHINA 周日为一周的第一天
     * @return
     */
    public static LocalDateTime getWeekLastDay(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.with(WeekFields.of(locale == null ? Locale.CHINA : locale).dayOfWeek(), 7);
    }


    /**
     * 计算两个日期之间相差年数
     *
     * @param smallDateTime 较小的时间
     * @param bigDateTime   较大的时间
     * @return 相差年数
     */
    public static int getYearDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.YEARS);
    }

    /**
     * 计算两个日期之间相差月数
     *
     * @param smallDateTime 较小的时间
     * @param bigDateTime   较大的时间
     * @return 相差月数
     */
    public static int getMonthDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.MONTHS);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smallDateTime 较小的时间
     * @param bigDateTime   较大的时间
     * @return 相差天数
     */
    public static int getDayDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.DAYS);
    }

    /**
     * 计算两个日期之间相差小时数
     *
     * @param smallDateTime 较小的时间
     * @param bigDateTime   较大的时间
     * @return 相差小时数
     */
    public static int getHourDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.HOURS);
    }

    /**
     * 计算两个日期之间相差分钟数
     *
     * @param smallDateTime
     * @param bigDateTime
     * @return 相差分钟数
     */
    public static int getMinutesDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.MINUTES);
    }

    /**
     * 计算两个日期之间相差秒数
     *
     * @param smallDateTime
     * @param bigDateTime
     * @return 相差秒数
     */
    public static int getSecondsDiff(LocalDateTime smallDateTime, LocalDateTime bigDateTime) {
        return (int) smallDateTime.until(bigDateTime, ChronoUnit.SECONDS);
    }

    /**
     * 获取月
     * @param localDateTime
     * @return
     */
    public static int getMonth(LocalDateTime localDateTime) {
        return localDateTime.getMonthValue();
    }

    /**
     * 获取年
     * @param localDateTime
     * @return
     */
    public static int getYear(LocalDateTime localDateTime) {
        return localDateTime.getYear();
    }

    /**
     * 获取日
     * @param localDateTime
     * @return
     */
    public static int getDay(LocalDateTime localDateTime) {
        return localDateTime.getDayOfMonth();
    }

    /**
     * 获取小时
     * @param localDateTime
     * @return
     */
    public static int getHours(LocalDateTime localDateTime) {
        return localDateTime.getHour();
    }


    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(getDay(now));
    }

}