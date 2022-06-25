package ru.netology.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.UserData;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    private UserData user = new UserData();
    private LoginPage loginPage = new LoginPage();

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldHappyPath() {
        var verifyPage = loginPage.login(user.getId(), user.getPassword());
        verifyPage.verify(user.getId(), true);
        var dashboard = verifyPage.successVerify();
    }
}
