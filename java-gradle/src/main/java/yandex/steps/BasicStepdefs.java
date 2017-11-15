package yandex.steps;

import com.codeborne.selenide.*;
import com.codeborne.selenide.commands.PressEnter;
import cucumber.api.PendingException;
import cucumber.api.java.ru.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yandex.pages.YandexMarketPage;
import yandex.pages.YandexStartPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static org.junit.Assert.fail;

public class BasicStepdefs {

    HashMap<String, String> hashMemory = new HashMap<>();
    YandexStartPage yandexStartPage = page(YandexStartPage.class);
    YandexMarketPage yandexMarketPage = page(YandexMarketPage.class);

    @И("^Зайти на \"([^\"]*)\"$")
    public void goToSelectedPageByLink(String url) {
        open(url);
    }

    @И("^выполнено ожидание в течение (\\d+) секунд$")
    public void waitDuring(int seconds) {
        Selenide.sleep(1000 * seconds);
    }

    @И("^нажата ссылка \"([^\"]*)\"$")
    public void goToLink(String linkName) {
//        $(By.xpath("//*[text()='" + linkName + "']"))
//                .shouldBe(Condition.appear).click();
        yandexMarketPage.clickOnLink(linkName);
    }

    @И("^выполнен переход на \"([^\"]*)\"$")
    public void goToTab(String linkName) {
//        $(By.xpath("//*[text()='" + linkName + "']"))
//                .shouldBe(Condition.appear).click();
        yandexStartPage.marketLink.shouldBe(Condition.appear).click();
    }

    @И("^выбрана категория \"([^\"]*)\"$")
    public void goToCategory(String linkName) {
        Actions action = new Actions(WebDriverRunner.getWebDriver());
        action.moveToElement($(By.xpath("//*[text()='Электроника']")));
        sleep(500);
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Наушники']")));

        SelenideElement se = $(By.xpath("//*[text()='Наушники']"))
                .waitUntil(Condition.exist, 30)
                .shouldBe(Condition.appear);
        se.click();
    }

    @И("^навестись на ссылку \"([^\"]*)\"$")
    public void chooseLinkFromMenu(String linkName) {
        Actions action = new Actions(WebDriverRunner.getWebDriver());
        action.moveToElement($(By.xpath("//*[text()='" + linkName + "']")));
    }

    @И("^кликнуть на ссылку \"([^\"]*)\"$")
    public void clickLinkFromMenu(String linkName) {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='" + linkName + "']")));
        SelenideElement se = $(By.xpath("//*[text()='" + linkName + "']"))
                .waitUntil(Condition.exist, 30)
                .shouldBe(Condition.appear);
        se.click();
    }

    @И("^нажата кнопка \"([^\"]*)\"$")
    public void clickToButton(String btnName) {
        $(By.xpath("//button/span[text()='" + btnName + "']"))
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .shouldBe(Condition.appear)
                .click();
    }

    @И("^выбрано из списка \"([^\"]*)\"$")
    public void choseLinkFromMenu(String linkName) {
//        $(By.xpath("//div[@class='catalog-menu__list']/child::a[contains(@class,'catalog-menu__list-item') and text()='"+linkName+"']"))
//                .shouldBe(Condition.appear)
//                .click();
        yandexMarketPage.goToTab(linkName);
    }

    @И("^установлена цена \"([^\"]*)\" на значение \"([^\"]*)\"$")
    public void setPrice(String field, String value) {
//        $(By.xpath("//*[@sign-title='" + field + "']/child::node()/input"))
//                .shouldBe(Condition.appear)
//                .sendKeys(value);
        yandexMarketPage.cost.shouldBe(Condition.appear).sendKeys(value);
    }

    @И("отмечен чекбокс")
    public void setPrice(List<String> field) {
        for (String item : field) {
            yandexMarketPage.checkboxChecked(item);
        }

//        $(By.xpath("//*[@class='checkbox__label' and text()='" + field + "']"))
//                .shouldBe(Condition.enabled)
//                .shouldBe(Condition.visible)
//                .shouldBe(Condition.appear)
//                .click();

    }

    @И("^отображается \"([^\"]*)\" элементов$")
    public void checkResult(int count) throws Throwable {
        int countOfResults = yandexMarketPage.productsOnPage.size();
        Assert.assertTrue(format("Несоответствие количества элементов. Ожидаемый результат: %s, текущий результат: %s",
                count, countOfResults), countOfResults == count);
    }

    @И("^Запомнить первый элемент в списке \"([^\"]*)\"$")
    public void memResult(String item) {
        hashMemory.put(item, yandexMarketPage.nameProductList.get(1).waitUntil(Condition.visible, 5000).getText().trim()/*;element.getText().trim()*/);
    }

    @И("^выполнить поиск по элементу \"([^\"]*)\"$")
    public void searcher(String item) {
        SelenideElement element = yandexMarketPage.searchString;
        element.shouldBe(Condition.appear)
                .sendKeys(hashMemory
                        .get(item));
        element.sendKeys(Keys.RETURN);
    }

    @И("^Наименование товара соотвествует запомненному значению \"([^\"]*)\"$")
    public void checkFindRes(String item) throws Throwable {
        String nameOfResult = yandexMarketPage.nameProduct
                .shouldBe(Condition.appear)
                .getText().trim();
        Assert.assertTrue(format("Несоответствие названий товаров. Ожидаемый результат: %s, текущий результат: %s",
                hashMemory.get(item), nameOfResult), nameOfResult.equals(hashMemory.get(item)));
    }

    @И("Верно отсортированы цены")
    public void getPrice() {
        Assert.assertTrue("Несоответствие сортировки цен", sortPrices(yandexMarketPage.prices));
    }

    private boolean sortPrices(List<SelenideElement> prices) {
        ArrayList<Integer> pricesInt = new ArrayList<>();
        Pattern p = Pattern.compile("-?\\d+");
        for (SelenideElement element : prices) {
            Matcher m = p.matcher(element.getText());
            while (m.find()) {
                pricesInt.add(Integer.valueOf(m.group()));
            }
        }
        ArrayList<Integer> tempPrices = pricesInt;
        Collections.sort(tempPrices);
        return pricesInt.equals(tempPrices);
    }
}
