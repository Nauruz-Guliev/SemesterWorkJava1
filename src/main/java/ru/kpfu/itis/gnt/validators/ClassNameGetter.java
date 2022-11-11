package ru.kpfu.itis.gnt.validators;

public class ClassNameGetter {
    public static String getClassName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }
}
