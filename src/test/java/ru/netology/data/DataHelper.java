package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class UserData {
        private final String login;
        private final String password;
    }

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    @Value
    public static class VerifyCode {
        private final String verifyCode;
    }

    public static VerifyCode getValidCode(String login) {
        return new VerifyCode(SQLHelper.getVerifyCodeByLogin(login, "1"));
    }

    public static VerifyCode getRandomCode() {
        return new VerifyCode(String.valueOf(faker.number().numberBetween(100_000, 999_999)));
    }
}
