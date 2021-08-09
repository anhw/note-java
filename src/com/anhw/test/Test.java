package com.anhw.test;

/**
 * @Author: anhw
 * @Date: 2020/4/29 0029 14:25
 */
public class Test {
//    private static final Object obj = new Object();
//    public static void main(String[] args) {
//        synchronized (obj) {
//            obj.hashCode();
//
//        }
//    }

//    public void foo(Object obj) {
//        synchronized (obj) {
//            obj.hashCode();
//        }
//    }

    public synchronized void foo(Object lock) {
        lock.hashCode();
    }
}
