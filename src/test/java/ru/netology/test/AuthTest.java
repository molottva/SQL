package ru.netology.test;

import com.github.javafaker.Faker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.SQLHelper;
import ru.netology.data.UserData;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertEquals;
import static ru.netology.data.SQLHelper.*;

public class AuthTest {
    UserData user;
    LoginPage loginPage;
    Faker faker = new Faker();

    @BeforeMethod
    public void setUp() {
        reloadVerifyCodeTable();
        open("http://localhost:9999/");
        user = new UserData();
        loginPage = new LoginPage();
    }

    @AfterClass
    public void setDown() {
        SQLHelper.setDown();
    }

    @Test
    public void shouldAuth() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.login(hidden);
        verifyPage.insert(getVerifyCodeByLogin(user.getLogin(), "1"));
        var dashboardPage = verifyPage.verify(hidden);
    }

    @Test
    public void shouldNoAuthWithOldestVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.login(hidden);
        verifyPage.insert(getVerifyCodeByLogin(user.getLogin(), "1"));
        var dashboardPage = verifyPage.verify(hidden);

        open("http://localhost:9999/");
        loginPage.insert(user.getLogin(), user.getPassword());
        verifyPage = loginPage.login(hidden);
        verifyPage.insert(getVerifyCodeByLogin(user.getLogin(), "1, 1"));
        dashboardPage = verifyPage.verify(visible);
    }

    @Test
    public void shouldNoAuthWithInvalidVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.login(hidden);
        verifyPage.insert(String.valueOf(faker.number().numberBetween(10_000, 999_999)));
        var dashboardPage = verifyPage.verify(visible);
    }

    @Test
    public void shouldNoAuthWithInvalidPassword() {
        loginPage.insert(user.getLogin(), faker.internet().password());
        var verifyPage = loginPage.login(visible);
    }

    @Test
    public void shouldNoAuthWithInvalidLogin() {
        loginPage.insert(faker.name().username(), user.getPassword());
        var verifyPage = loginPage.login(visible);
    }

    //todo bug
    @Test
    public void shouldBlockUserAfterThreeInputInvalidVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.login(hidden);
        verifyPage.insert(String.valueOf(faker.number().numberBetween(10_000, 999_999)));
        var dashboardPage = verifyPage.verify(visible);

        open("http://localhost:9999/");
        loginPage.insert(user.getLogin(), user.getPassword());
        verifyPage = loginPage.login(hidden);
        verifyPage.insert(String.valueOf(faker.number().numberBetween(10_000, 999_999)));
        dashboardPage = verifyPage.verify(visible);

        open("http://localhost:9999/");
        loginPage.insert(user.getLogin(), user.getPassword());
        verifyPage = loginPage.login(hidden);
        verifyPage.insert(String.valueOf(faker.number().numberBetween(10_000, 999_999)));
        dashboardPage = verifyPage.verify(visible);

        assertEquals(getUserStatus(user.getLogin()), "blocked");
    }

    @Test
    public void shouldNotificationWithEmptyVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.login(hidden);
        verifyPage.insert(null);
    }

    @Test
    public void shouldNotificationWithEmptyPassword() {
        loginPage.insert(user.getLogin(), null);
    }

    @Test
    public void shouldNotificationWithEmptyLogin() {
        loginPage.insert(null, user.getPassword());
    }
}
