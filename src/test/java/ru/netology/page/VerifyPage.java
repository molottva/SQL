package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class VerifyPage {
    private SelenideElement codeInput = $x("//span[@data-test-id='code']//input");
    private SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");

    public VerifyPage() {
        codeInput.should(visible);
        verifyButton.should(visible);
    }

    public void verify(String id, boolean newest) {
        codeInput.val(SQLHelper.getVerifyCodeByUserId(id, newest));
        verifyButton.click();
    }

    public DashboardPage successVerify() {
        return new DashboardPage();
    }
}
