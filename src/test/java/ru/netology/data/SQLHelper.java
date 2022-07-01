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
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void setDown() {
        setUp();
        reloadVerifyCodeTable();
        var sqlQueryOne = "DELETE FROM card_transactions;";
        var sqlQueryTwo = "DELETE FROM cards;";
        var sqlQueryThree = "DELETE FROM users;";
        runner.update(conn, sqlQueryOne);
        runner.update(conn, sqlQueryTwo);
        runner.update(conn, sqlQueryThree);
    }

    @SneakyThrows
    public static void reloadVerifyCodeTable() {
        setUp();
        var sqlQuery = "DELETE FROM auth_codes;";
        runner.update(conn, sqlQuery);
    }

    @SneakyThrows
    public static String getVerifyCodeByLogin(String login, String sqlLimit) {
        setUp();
        var sqlQuery = "SELECT code FROM auth_codes " +
                "JOIN users ON user_id = users.id " +
                "WHERE login IN (?) " +
                "ORDER BY created DESC LIMIT " + sqlLimit + ";";
        return runner.query(conn, sqlQuery, new ScalarHandler<String>(), login);
    }

    @SneakyThrows
    public static String getUserStatus(String login) {
        setUp();
        var sqlQuery = "SELECT status FROM users WHERE login IN (?);";
        return runner.query(conn, sqlQuery, new ScalarHandler<String>(), login);
    }

    @SneakyThrows
    public static void setUserStatus(String login, String userStatus) {
        setUp();
        var sqlQuery = "UPDATE users SET status = '" + userStatus + "' WHERE login IN(?);";
        runner.update(conn, sqlQuery, login);
    }
}
