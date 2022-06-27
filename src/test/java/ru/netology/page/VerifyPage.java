package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class VerifyPage {
    private SelenideElement codeInput = $x("//span[@data-test-id='code']//input");
    private SelenideElement codeInputEmptyNotification = $x(
            "//span[@data-test-id='code']//span[@class='input__sub']");
    private SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public VerifyPage(Condition status) {
        codeInput.should(status);
        verifyButton.should(status);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void insert(String code) {
        codeInput.val(code);
        verifyButton.click();
        if (code == null) {
            codeInputEmptyNotification.should(text("Поле обязательно для заполнения"));
        } else {
            codeInputEmptyNotification.shouldNot(exist);
        }
    }

    public DashboardPage verify(Condition status) {
        errorNotification.should(status);
        errorButton.should(status);
        if (status.equals(visible)) {
            errorButton.click();
            errorNotification.should(hidden);
            errorButton.should(hidden);
            return new DashboardPage(hidden);
        }
        return new DashboardPage(visible);
    }
}
