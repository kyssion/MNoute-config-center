package org.mouter.admin.util;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.mirror.reflection.mirror.MirrorObject;
import org.mouter.admin.data.answer.ConfigInformationData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {
    public static boolean isNullOrEmpty(Object...objects){
        for(Object item : objects){
            if(item==null){
                return true;
            }
        }
        return false;
    }

    public static <T> T mergeRow(Row row,T t) {
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

    public static <T> T mergeObject(T to, T from) {
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

    public static <T> List<T> getDataFrom(RowSet<Row> result,Class<T> t) {
        List<T> appList =
                new ArrayList<>();
        for(Row row:result){
            T appData = null;
            try {
                appData = ObjectUtils.mergeRow(row,t.getDeclaredConstructor().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            appList.add(appData);
        }
        return appList;
    }
}
