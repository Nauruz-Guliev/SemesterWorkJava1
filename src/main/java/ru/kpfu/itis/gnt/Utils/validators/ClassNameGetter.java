package ru.kpfu.itis.gnt.Utils.validators;

public class ClassNameGetter {
    public static String getClassName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }
}
