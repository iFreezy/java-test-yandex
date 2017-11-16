package yandex.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class YandexMarketPage {
    @FindBy(xpath = "//span[@sign-title='от']/child::node()/input")
    public SelenideElement cost;

    @FindBy(xpath = "//input[@id='header-search']")
    public SelenideElement searchString;

    @FindBy(css = ".n-title__text")
    public SelenideElement nameProduct;

    @FindBy(xpath = "//div[@class=\"n-snippet-cell2__title\"]/child::a")
    public List<SelenideElement> nameProductList;

    @FindBy(css = ".n-snippet-cell2__header")
    public List<SelenideElement> productsOnPage;

    @FindBy(xpath = "//*[@class='snippet-cell__price']/child::span[@class='price']")
    public List<SelenideElement> prices;

    public YandexMarketPage checkboxChecked(String name) {
        $(By.xpath("//*[@class='checkbox__label' and text()='" + name + "']")).shouldBe(Condition.visible).click();
        return page(YandexMarketPage.class);
    }

    public YandexMarketPage clickOnLink(String link) {
        $(By.linkText(link)).shouldBe(Condition.visible).click();
        return page(YandexMarketPage.class);
    }

    public void goToTab(String name) {
        $(By.xpath("//div[@class='catalog-menu__list']/child::a[contains(@class,'catalog-menu__list-item') and text()='" + name + "']"))
                .shouldBe(Condition.visible)
                .click();
    }
}
