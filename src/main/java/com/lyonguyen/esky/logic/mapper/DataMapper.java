package com.lyonguyen.esky.logic.mapper;

import com.lyonguyen.esky.logic.enums.AnswerType;
import com.lyonguyen.esky.logic.enums.Difficulty;
import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.database.DataRow;
import com.lyonguyen.esky.database.DataTable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class DataMapper {

    private DataEncoder encoder = new DataEncoder();

    public <T> List<T> mapDataTable(DataTable table, Class<T> type) {
        List<T> list = new Vector<>();

        for(DataRow row : table) {
            T result = mapDataRow(row, type);
            if(result != null) {
                list.add(result);
            }
        }
        return list;
    }

    public <T> T mapDataRow(DataRow row, Class<T> type) {
        Set<String> fieldNames = row.keySet();

        ReflectionObject<T> result;
        try {
            result = new ReflectionObject(type);
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

        for(String fieldName : fieldNames) {
            try {
                Class<?> fieldType = result.getFieldType(fieldName);

                Object value = row.column(fieldName);

                if(value == null) continue;

                Class<?> valueType = value.getClass();

                if(!valueType.equals(fieldType)) {
                    value = convertType(value, fieldType);
                }

                result.set(fieldName, value);
            } catch (NoSuchFieldException e) {
                continue;
            } catch (NoSuchMethodException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        return result.getObject();
    }

    private <T> Object convertType(Object value, Class<T> type) {
        if(type.equals(String.class)) {

            return value.toString();
        }
        if(value instanceof String) {
            if(type.equals(Role.class)) {

                return toRole((String) value);
            }
            if(type.equals(AnswerType.class)) {

                return toAnswerType((String) value);
            }
            if(type.equals(Difficulty.class)) {

                return toDifficulty((String) value);
            }
            if(type.equals(List.class)) {

                return encoder.decompress((String) value);
            }
        }

        return value;
    }

    private Role toRole(String value) {
        return Role.valueOf(value);
    }

    private AnswerType toAnswerType(String value) {
        return AnswerType.valueOf(value);
    }

    public Difficulty toDifficulty(String value) {
        return Difficulty.valueOf(value);
    }
}
