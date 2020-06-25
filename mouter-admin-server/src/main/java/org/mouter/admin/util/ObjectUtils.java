package org.mouter.admin.util;

import io.vertx.sqlclient.Row;
import org.mintflow.param.ParamWrapper;
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
        Class tClass = t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for(Field field:fields){
            Class<?> type = field.getType();
            String name = StringUtils.humpToLine2(field.getName());
            if(type==Integer.class){
                Integer data = row.getInteger(name);
                field.set(t,data);
            }else if(type == Long.class){
                Long data = row.getLong(name);
                field.set(t,data);
            }else if(type==String.class){
                String data = row.getString(name);
                field.set(t,data);
            }
        }
        return t;
    }

    public static <T> T mergeObject(T appDataUpdate, T appData) throws IllegalAccessException {
        Class tClass = appDataUpdate.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for(Field field:fields){
            Object now = field.get(appData);
            if(now!=null){
                field.set(appDataUpdate,field.get(appData));
            }
        }
        return appDataUpdate;
    }
}
