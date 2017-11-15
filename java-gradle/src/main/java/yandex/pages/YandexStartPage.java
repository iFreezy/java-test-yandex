package yandex.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.*;

public class YandexStartPage {

    @FindBy(xpath = "//*[text()='Маркет']")
    public SelenideElement marketLink;

}

