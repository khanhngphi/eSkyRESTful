package com.lyonguyen.esky.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class DataTable extends Vector<DataRow> {

    public static DataTable from(ResultSet rs) throws SQLException {

        if (rs == null) {
            return null;
        }

        DataTable table = new DataTable();

        while (rs.next()) {

            DataRow row = DataRow.from(rs);

            table.add(row);
        }

        return table;
    }

    public List<Object> toList(String column) {
        List<Object> list = new Vector<>();
        for (DataRow row : this) {
            list.add(row.column(column));
        }
        return list;
    }

    public <T> List<T> toList(String column, Class<T> type) {
        List<T> list = new Vector<>();
        for (DataRow row : this) {
            list.add(row.column(column, type));
        }
        return list;
    }

    public DataRow row(int index) {
        return get(index);
    }
}
