package org.mouter.admin.util;

import io.netty.util.internal.StringUtil;

public final class StringUtils {
    public static boolean isNullOrEmpty(String...strings){
        for(String item : strings){
            if(StringUtil.isNullOrEmpty(item)){
                return true;
            }
        }
        return false;
    }
}
