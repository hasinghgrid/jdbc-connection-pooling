package com.example.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;

public class SingleConnectionDataSource implements DataSource {
    private final Connection connection;

    public SingleConnectionDataSource(String url, String user, String pass) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, pass);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) {
        return connection;
    }

    // Other methods left unimplemented
    public PrintWriter getLogWriter() { throw new UnsupportedOperationException(); }
    public void setLogWriter(PrintWriter out) { throw new UnsupportedOperationException(); }
    public void setLoginTimeout(int seconds) { throw new UnsupportedOperationException(); }
    public int getLoginTimeout() { throw new UnsupportedOperationException(); }
    public java.util.logging.Logger getParentLogger() { throw new UnsupportedOperationException(); }
    public <T> T unwrap(Class<T> iface) { throw new UnsupportedOperationException(); }
    public boolean isWrapperFor(Class<?> iface) { throw new UnsupportedOperationException(); }
}
