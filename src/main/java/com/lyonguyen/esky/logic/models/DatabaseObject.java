package com.lyonguyen.esky.logic.models;

import com.lyonguyen.esky.logic.mapper.ReflectionObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class DatabaseObject {

    private ReflectionObject object;

    public void trim() {
        if (object == null) {
            object = new ReflectionObject(this);
        }
        Field[] fields = object.getFields();
        for (Field field : fields) {
            try {
                if (field.getType().equals(String.class)) {
                    String value = (String) object.<String>get(field);
                    if (value != null) {
                        object.set(field, value.trim());
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
    }
}
