package com.yehuijie.homophone.util;

/**
 * Created by cent on 2018/3/1.
 */

import java.util.Collection;
import java.util.Map;

/**
 * 对象空判断工具类
 * Created by cent on 2017/1/5.
 */
public enum BlankUtil {
    ;

    /**
     * 集合判空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null) {
            return true;
        } else if (collection.size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 字符串判空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Map判空
     *
     * @param t
     * @return
     */
    public static <T extends Map> boolean isEmpty(T t) {
        if (t == null) {
            return true;
        } else if (t.keySet().size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 对象判空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isEmpty((String) obj);
        }

        return false;
    }

    /**
     * 数组判空
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object[] objs) {
        if (objs == null) {
            return true;
        }
        if (objs.length <= 0) {
            return true;
        }

        return false;
    }

    /**
     * 不定参判空（只要有一个为空即返回true）
     * @param objs
     * @return
     */
    public static boolean isExistsEmpty(Object...objs) {
        for(Object obj:objs){
            if(isEmpty(obj)){
                return true;
            }
        }
        return false;
    }

    /**
     * 集合非空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 字符串非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Map非空
     *
     * @param t
     * @return
     */
    public static <T extends Map> boolean isNotEmpty(T t) {
        return !isEmpty(t);
    }

    /**
     * 对象非空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 数组非空
     *
     * @param objs
     * @return
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }
}
