package com.lyonguyen.esky.logic.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionObject<T> {

    private T object;

    private Field[] fields;

    private Method[] methods;

    public ReflectionObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        object = clazz.newInstance();
        init();
    }

    public ReflectionObject(T object) {
        this.object = object;
        init();
    }

    private void init() {
        fields = object.getClass().getDeclaredFields();
        methods = object.getClass().getDeclaredMethods();
    }

    public T getObject() {
        return object;
    }

    public boolean has(String name) {
        for (Field field : fields) {
            if(field.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public <V> void set(String name, V value) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field field = getField(name);
        if(field.isAccessible()) {
            field.set(object, value);
        } else {
            String methodName = getSetterMethodName(name);
            Method method = getMethod(methodName, field.getType());
            method.invoke(object, value);
        }
    }

    public <V> void set(Field field, V value) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(field.isAccessible()) {
            field.set(object, value);
        } else {
            String methodName = getSetterMethodName(field.getName());
            Method method = getMethod(methodName, field.getType());
            method.invoke(object, value);
        }
    }

    public <V> V get(String name) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field field = getField(name);
        if(field.isAccessible()) {
            return (V) field.get(object);
        } else {
            String methodName = getGetterMethodName(name);
            return (V) invoke(methodName);
        }
    }

    public <V> V get(Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(field.isAccessible()) {
            return (V) field.get(object);
        } else {
            String methodName = getGetterMethodName(field.getName());
            return (V) invoke(methodName);
        }
    }

    public Field getField(String name) throws NoSuchFieldException {
        return object.getClass().getDeclaredField(name);
    }

    public Field[] getFields() {
        return fields;
    }

    public Class<?> getFieldType(String name) throws NoSuchFieldException {
        return object.getClass().getDeclaredField(name).getType();
    }

    public Method getMethod(String name) throws NoSuchMethodException {
        return object.getClass().getDeclaredMethod(name);
    }

    public Method getMethod(String name, Class<?> paramType) throws NoSuchMethodException {
        return object.getClass().getDeclaredMethod(name, paramType);
    }

    public Method[] getMethods() {
        return methods;
    }

    public <V> V invoke(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (V) getMethod(name).invoke(object);
    }

    public <V, P> V invoke(String name, P param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (V) getMethod(name, param.getClass()).invoke(object, param);
    }

    private String getGetterMethodName(String name) {
        return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String getSetterMethodName(String name) {
        return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
