package com.leadtone.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * PinGen pin码生成规则
 *
 * @author ChangJun
 * @version 1.0
 */
public class PinGen {

    private static int loopNum;
    private static Long batchNo;

    public static Long getBatchNo() {
        if(batchNo == null) {
            batchNo = getSerialPin();
        }
        return batchNo;
    }

    public static void setBatchNo(Long batchNo) {
        PinGen.batchNo = batchNo;
    }
    
    public static long getBasePin() {
        return createPin(0); // 0生成基本pin码
    }

    public static long getMerchantPin() {
        return createPin(1); // 1表示商户
    }

    public static long getCustomerPin() {
        return createPin(2); // 2表示客户
    }

    public static long getSerialPin() {
        return createPin(8); // 8表示流水号
    }

    private static long createPin(int type) {
        DecimalFormat numFormat = (DecimalFormat) DecimalFormat.getInstance(); // 格式化数字位数

        Calendar cal = Calendar.getInstance(); // 获取当前时间

        StringBuffer sBuffer = new StringBuffer();
        if (type != 0) {
            sBuffer.append(type); // 2代表客户
        }
        int year = cal.get(Calendar.YEAR);// 年 2位
        sBuffer.append(Integer.toString(year).substring(2));

        int day = cal.get(Calendar.DAY_OF_YEAR); // 日 3位 一年当中的第几天
        numFormat.applyPattern("000");
        sBuffer.append(numFormat.format(day));

        int second = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60
                + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND); // 秒
        // 5位
        // 一天当中的第多少秒
        numFormat.applyPattern("00000");
        sBuffer.append(numFormat.format(second));

        int millisecond = cal.get(Calendar.MILLISECOND); // 毫秒 3位
        numFormat.applyPattern("000");
        sBuffer.append(numFormat.format(millisecond));

        Random r = new Random();
        int randomNum = r.nextInt(99999); // 随机数 5位

        numFormat.applyPattern("00000");
        sBuffer.append(numFormat.format(randomNum));
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Long.parseLong(sBuffer.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        HashSet<Long> s = new HashSet();
        for (int i = 0; i < 3000; i++) {
            s.add(PinGen.getSerialPin());
//    		Thread.sleep(1);
        }
        System.out.print(s.size());
    }
}
