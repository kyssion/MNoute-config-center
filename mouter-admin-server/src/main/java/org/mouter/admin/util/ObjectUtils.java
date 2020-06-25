package org.mouter.admin.util;

public class ObjectUtils {
    public static boolean isNullOrEmpty(Object...objects){
        for(Object item : objects){
            if(item==null){
                return true;
            }
        }
        return false;
    }
}
