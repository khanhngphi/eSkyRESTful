package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.logic.mapper.DataEncoder;
import com.lyonguyen.esky.logic.mapper.HashIdsWrap;
import com.lyonguyen.esky.logic.mapper.DataMapper;
import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataRow;
import com.lyonguyen.esky.database.DataTable;
import com.lyonguyen.esky.logic.models.Lesson;

import java.util.List;
import java.util.Vector;

public abstract class DataAccessObject {

    private DataProvider dataProvider;

    protected DataMapper mapper;

    protected DataEncoder encoder;

    protected DataAccessObject(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
        mapper = new DataMapper();
        encoder = new DataEncoder();
    }

    protected DataTable executeQuery(String sql, Object... params) {
        return dataProvider.executeQuery(sql, params);
    }

    protected Object executeScalar(String sql, Object... params) {
        return dataProvider.executeScalar(sql, params);
    }

    protected <T> T executeScalar(Class<T> type, String sql, Object... params) {
        return dataProvider.executeScalar(type, sql, params);
    }

    protected Integer executeUpdate(String sql, Object... params) {
        return dataProvider.executeUpdate(sql, params);
    }


}
