package com.lyonguyen.esky.database;

import java.sql.*;

public class DataProvider {

    public static final String DRIVER = "com.mysql.jdbc.Driver";

    private String url;

    private String username;

    private String password;

    public DataProvider() {}

    public DataProvider(String url, String username, String password) {
        init(url, username, password);
    }

    public boolean init(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

        Connection conn = getConnection();

        if (conn == null) {
            return false;
        }

        try {
            conn.close();
        } catch (SQLException ex) {
            printError(ex);
        }

        return true;
    }

    private Connection getConnection() {

        Connection conn = null;

        try {

            Class.forName(DRIVER);

            conn = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            printError(ex);
        }

        return conn;
    }

    public DataTable executeQuery(String sql, Object... parameters) {

        Connection conn = getConnection();

        if (conn == null) {
            return null;
        }

        PreparedStatement pstmt = null;

        ResultSet rs = null;

        DataTable table = null;

        try {

            pstmt = conn.prepareStatement(sql);

            for(int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            rs = pstmt.executeQuery();

            table = DataTable.from(rs);

        } catch (SQLException ex) {
            printError(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            return table;
        }

    }

    public Object executeScalar(String sql, Object... parameters) {
        return executeScalar(Object.class, sql, parameters);
    }

    public <T> T executeScalar(Class<T> type, String sql, Object... parameters) {

        Connection conn = getConnection();

        if (conn == null) {
            return null;
        }

        PreparedStatement pstmt = null;

        ResultSet rs = null;

        T result = null;

        try {

            pstmt = conn.prepareStatement(sql);

            for(int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                if(type.equals(Object.class)) {
                    result = (T) rs.getObject(1);
                } else {
                    result = rs.getObject(1, type);
                }
            }

        } catch (SQLException ex) {
            printError(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            return result;
        }
    }

    /** Return generated key if statement is INSERT or affected rows if statement is UPDATE, DELETE **/
    public int executeUpdate(String sql, Object... parameters) {

        Connection conn = getConnection();

        if (conn == null) {
            return 0;
        }

        PreparedStatement pstmt = null;

        ResultSet rs = null;

        int result = 0;

        try {

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for(int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            result = pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                result = rs.getInt("GENERATED_KEY");
            }

        } catch (SQLException ex) {
            printError(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    printError(ex);
                }
            }

            return result;
        }
    }

    private void printError(SQLException ex) {
        System.out.println("Error message: " + ex.getMessage());
        System.out.println("SQL state: " + ex.getSQLState());
        System.out.println("Error code: " + ex.getErrorCode());
    }
}
