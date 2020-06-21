package com.yehuijie.homophone.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum ApiCommonUtil {
    ;
    private static final Logger logger = LoggerFactory.getLogger(ApiCommonUtil.class);


    /**
     * 分隔多值参数，根据特定分隔符
     *
     * @param params
     * @return
     */
    public static List<String> splitMultiParams(String params) {
        List<String> paramList = new LinkedList<>();
        if (BlankUtil.isEmpty(params)) {
            return paramList;
        }
        paramList.addAll(Arrays.asList(params.trim().split(",")));

        return paramList;
    }

    /**
     * 根据拼音比较两个字符串
     *
     * @param str1
     * @param str2
     * @return 大于返回大于0的数值，小于返回小于0的数值，等于返回0
     */
    public static int compareStringWithPinyin(String str1, String str2) {
        String py1 = strToPinyin(str1);
        String py2 = strToPinyin(str2);
        return py1.compareTo(py2);
    }

    /**
     * 字符串转化为拼音（对中文转化）
     *
     * @param str
     * @return
     */
    public static String strToPinyin(String str) {
        String py = "";
        if (BlankUtil.isEmpty(str)) {
            return py;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String[] pys = PinyinHelper.toHanyuPinyinStringArray(ch);
            if (BlankUtil.isEmpty(pys)) {
                py += ch;
            } else {
                py += pys[0].substring(0, pys[0].length() - 1);
            }
        }
        return py;
    }

    /**
     * 关闭输出流
     *
     * @param out
     */
    public static void closeOutput(OutputStream out) {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                logger.error("关闭输出流报错：{}",e.getMessage(), e);
            }
        }
    }

    /**
     * 计算抽样间隔
     *
     * @param beginTime
     * @param endTime
     * @return
     */


}
