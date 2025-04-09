package com.example;

import com.example.datasource.SingleConnectionDataSource;
import com.example.task.QueryTask;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;


public class Main {
    private static final Dotenv dotenv = Dotenv.load();

     static final String DB_URL = dotenv.get("DB_URL");
     static final String MYSQL_USER = dotenv.get("MYSQL_USER");
     static final String MYSQL_PASSWORD = dotenv.get("MYSQL_PASSWORD");

    public static void main(String[] args) throws SQLException, InterruptedException {
        System.out.println("===> Using SingleConnectionDataSource");
        runWithDataSource(new SingleConnectionDataSource(DB_URL, MYSQL_USER, MYSQL_PASSWORD));

        System.out.println("\n===> Using HikariCP Connection Pool");
        runWithDataSource(getHikariDataSource());
    }

    private static void runWithDataSource(DataSource ds) throws InterruptedException {
        int threadCount = 5;
        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            Thread t = new Thread(new QueryTask(ds), "Worker-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) + " ms");
    }

    private static HikariDataSource getHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(MYSQL_USER);
        config.setPassword(MYSQL_PASSWORD);
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(10000);
        return new HikariDataSource(config);
    }
}
