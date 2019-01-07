package com.lyonguyen.esky.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class DataRow extends HashMap<String, Object> {

    public static DataRow from(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();

        DataRow row = new DataRow();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {

            row.put(rsmd.getColumnLabel(i), rs.getObject(i));

        }

        return row;
    }

    public Object column(String name) {
        return get(name);
    }

    public <T> T column(String name, Class<T> type) {
        return type.cast(get(name));
    }

    public boolean has(String name) {
        return containsKey(name);
    }

    public Class<?> getType(String name) {
        return get(name).getClass();
    }

}