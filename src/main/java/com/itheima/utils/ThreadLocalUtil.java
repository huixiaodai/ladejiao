package com.itheima.utils;

import java.util.Map;

public class ThreadLocalUtil {

    //提供ThreadLocal对象 这个对象本身就有方法：get()/set()/remove()
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get() { return (T) THREAD_LOCAL.get(); }

    //存储键值对
    public static void set(Object value) { THREAD_LOCAL.set(value); }

    //清除，防止内存泄露
    public static void remove() { THREAD_LOCAL.remove(); }

    //获取当前用户id
    public static Integer getCurrentId(){
        Map<String,Object> map = get();
        return map == null ? null : (Integer) map.get("id");
    }

    public static String getCurrentUsername() {
        Map<String, Object> map = get();
        return map == null ? null : (String) map.get("username");
    }
}
