package com.lyonguyen.esky.logic.mapper;

import com.lyonguyen.esky.logic.models.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class DataEncoder {

    private final List<String> ENCODE_FIELD = Collections.unmodifiableList(new Vector<String>(){{
        add("id");
        add("answered");
        add("remains");
    }});

    private final HashIdsWrap hash = new HashIdsWrap("Can you guess?", 10);

    public Integer decode(String id) {
        return hash.decode(id);
    }

    public String encode(Integer id) {
        return hash.encode(id);
    }

    public String encode(String id) {
        return hash.encode(id);
    }

    public List<String> decode(List<String> ids) {
        if(ids == null) {
            return null;
        }
        List<String> decodedIds = new Vector<>();
        for (String id : ids) {
            Integer decodedId = decode(id);
            if(decodedId != null) {
                decodedIds.add(decodedId.toString());
            }
        }
        return decodedIds;
    }

    public List<String> encode(List<String> ids) {
        if(ids == null) {
            return null;
        }
        List<String> encodedIds = new Vector<>();
        for (String id : ids) {
            try {
                String encodedId = encode(Integer.parseInt(id));
                encodedIds.add(encodedId);
            } catch (NumberFormatException ex) {
                continue;
            }
        }
        return encodedIds;
    }

    public <T extends DatabaseObject> T encodeObject(T o) {
        ReflectionObject<T> object = new ReflectionObject<T>(o);
        Field[] fields = object.getFields();
        for (Field field : fields) {
            String name = field.getName();
            if (ENCODE_FIELD.contains(name)) {
                try {
                    if(field.getType().equals(String.class)) {
                        String value = object.get(name);
                        object.set(name, encode(value));
                    } else if(field.getType().equals(List.class)) {
                        List value = object.get(name);
                        object.set(name, encode(value));
                    }
                } catch (NoSuchFieldException e) {
                    continue;
                } catch (NoSuchMethodException e) {
                    continue;
                } catch (InvocationTargetException e) {
                    continue;
                } catch (IllegalAccessException e) {
                    continue;
                }
            }
        }
        return object.getObject();
    }

    public <T extends DatabaseObject> List<T> encodeObject(List<T> objects) {
        for (T object : objects) {
            encodeObject(object);
        }
        return objects;
    }

    public String compress(List<String> value) {
        return compress(value, ";");
    }

    public String compress(List<String> value, String separator) {
        if(value == null || value.isEmpty()) {
            return null;
        }
        StringBuilder compressedValue = new StringBuilder();
        for(String s : value) {
            compressedValue.append(separator + s);
        }
        return compressedValue.substring(1);
    }

    public List<String> decompress(String value) {
        return decompress(value, ";");
    }

    public List<String> decompress(String value, String separator) {
        if(value == null) {
            return null;
        }
        return new Vector<>(Arrays.asList(value.split(separator)));
    }
}
