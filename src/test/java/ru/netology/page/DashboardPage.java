package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private SelenideElement dashboardHeader = $x("//h2[@data-test-id='dashboard']");

    public DashboardPage() {
        dashboardHeader.should(visible);
    }
}

