package com.example.task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryTask implements Runnable {
    private final DataSource dataSource;

    public QueryTask(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT SLEEP(2)")) {
            stmt.execute();
            System.out.println(Thread.currentThread().getName() + " finished sleep.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
