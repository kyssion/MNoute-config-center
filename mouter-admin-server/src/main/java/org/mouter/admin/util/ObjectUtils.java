package org.mouter.admin.util;

import io.vertx.sqlclient.Row;
import org.mintflow.param.ParamWrapper;
import org.mirror.reflection.mirror.MirrorObject;
import org.mouter.admin.data.ApplicationInformationData;

import java.lang.reflect.Field;

public class ObjectUtils {
    public static boolean isNullOrEmpty(Object...objects){
        for(Object item : objects){
            if(item==null){
                return true;
            }
        }
        return false;
    }

    public static <T> T mergeRow(Row row,T t) throws IllegalAccessException {
        MirrorObject mirrorObject = MirrorObject.forObject(t);
        String[] setterNames= mirrorObject.getSetterNames();
        for(String s:setterNames){
            Class<?> type = mirrorObject.getSetterType(s);
            String name =StringUtils.humpToLine2(s);
            if(type == String.class){
                mirrorObject.setValue(s,row.getString(name));
            }else if(type==Integer.class){
                mirrorObject.setValue(s,row.getInteger(name));
            }else if (type==Long.class){
                mirrorObject.setValue(s,row.getLong(name));
            }
        }
        return t;
    }

    public static <T> T mergeObject(T to, T from) throws IllegalAccessException {
        MirrorObject toObject = MirrorObject.forObject(to);
        MirrorObject fromObject = MirrorObject.forObject(from);
        String[] setNames = toObject.getSetterNames();
        for(String setName : setNames){
            Object now = fromObject.getValue(setName);
            if(now!=null){
                toObject.setValue(setName,now);
            }
        }
        return (T) toObject.getOriginalObject();
    }
}
