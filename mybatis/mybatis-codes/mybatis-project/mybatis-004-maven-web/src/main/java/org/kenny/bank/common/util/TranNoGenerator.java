package org.kenny.bank.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

// 一个基于时间戳 + 自增计数器的本地流水号生成算法，确保同一毫秒内最多生成 10000 个不重复的订单号/交易号
public class TranNoGenerator {

    //在多线程环境下，得到一个线程安全计数器
    private static final AtomicLong counter = new AtomicLong(0);

    public static synchronized String generate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timePart = sdf.format(new Date());
        long count = counter.incrementAndGet() % 10000;
        return timePart + String.format("%04d", count);
    }
}

