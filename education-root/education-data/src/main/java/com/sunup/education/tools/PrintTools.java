package com.sunup.education.tools;

public class PrintTools {

    public static void print(Object object) {
        System.out.println(object);
    }

    public static void print(Object... objects) {
        System.out.println(StringTools.bufferAppend(objects));
    }

    public static void main(String[] args) {
        print("aaa", "sss");
    }
}
