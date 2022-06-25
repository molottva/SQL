package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static ru.netology.data.SQLHelper.getLoginByUserId;

public class LoginPage {
    private SelenideElement loginInput = $x("//span[@data-test-id='login']//input");
    private SelenideElement passwordInput = $x("//span[@data-test-id='password']//input");
    private SelenideElement loginButton = $x("//button[@data-test-id='action-login']");

    public LoginPage() {
        loginInput.should(visible);
        passwordInput.should(visible);
        loginButton.should(visible);
    }

    public VerifyPage login(String id, String password) {
        loginInput.val(getLoginByUserId(id));
        passwordInput.val(password);
        loginButton.click();
        return new VerifyPage();
    }
}
