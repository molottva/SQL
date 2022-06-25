package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setup() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static String getLoginByUserId(String id) {
        setup();
        var loginSQL = "SELECT login FROM users WHERE id IN ('" + id + "');";
        var login = runner.query(conn, loginSQL, new ScalarHandler<String>());
        return login;
    }

    @SneakyThrows
    public static String getVerifyCodeByUserId(String id, boolean newest) {
        setup();
        String limit = (newest) ? "1": "1, 1";
        var codeSQL = "SELECT code FROM auth_codes " +
                "WHERE user_id IN ('" + id + "') " +
                "ORDER BY created DESC " +
                "LIMIT " + limit + ";";
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return code;
    }
}
