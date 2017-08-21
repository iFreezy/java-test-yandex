package gradle.cucumber;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import static com.codeborne.selenide.Selenide.*;

public class BasicStepdefs {

    HashMap<String, SelenideElement> hashMemory = new HashMap<>();

    //WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 20);

    @И("^Зайти на \"([^\"]*)\"$")
    public void goToSelectedPageByLink(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
        System.setProperty("selenide.browser", "Chrome");
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 20);
        WebDriverRunner.getWebDriver().get(url);
    }

    @Когда("^выполнено ожидание в течение (\\d+) секунд$")
    public void waitDuring(int seconds) {
        Selenide.sleep(1000 * seconds);
    }

    @И("^нажата ссылка \"([^\"]*)\"$")
    public void goToLink(String linkName) {
        $(By.xpath("//*[text()='" + linkName + "'][1]"))
                .shouldBe(Condition.appear).click();
    }

    @И("^нажата кнопка \"([^\"]*)\"$")
    public void clickToButton(String btnName) {
        $(By.xpath("//button/span[text()='" + btnName + "']"))
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .shouldBe(Condition.appear)
                .click();
    }

    @И("^навестись на ссылку \"([^\"]*)\"$")
    public void chooseLinkFromMenu(String linkName) {
        Actions action = new Actions(WebDriverRunner.getWebDriver());
        action.moveToElement($(By.xpath("//*[text()='Электроника']")));
    }
        //
        sleep(500);
    @И("^кликнуть на ссылку \"([^\"]*)\"$")
    public void clickLinkFromMenu(String linkName) {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Наушники']")));
        SelenideElement se = $(By.xpath("//*[text()='Наушники']"))
                .waitUntil(Condition.exist, 30)
                .shouldBe(Condition.appear);
        se.click();
    }

    @И("^установлена цена \"([^\"]*)\" на значение \"([^\"]*)\"$")
    public void setPrice(String field, String value) {
        $(By.xpath("//*[@sign-title='" + field + "']/child::node()/input"))
                .shouldBe(Condition.appear)
                .sendKeys(value);
    }

    @И("^отмечен чекбокс \"([^\"]*)\"$")
    public void setPrice(String field) {
        $(By.xpath("//*[@class='checkbox__label' and text()='" + field + "']"))
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .shouldBe(Condition.appear)
                .click();

    }

    @И("^отображается \"([^\"]*)\" элементов$")
    public void checkResult(int count) {
        int countOfResults = $$(By.xpath("//h4[@class='title title_size_15']"))
                .size();
        if (countOfResults == count){
            System.out.print("Количество совпало");
        }else{
            System.out.print("Количество не совпало. Нашлось " + countOfResults + " ; Ожидалось " + count);
        }

    }


    @И("^Запомнить первый элемент в списке \"([^\"]*)\"$")
    public void memResult(String item) {
         SelenideElement element = $(By.xpath("//h4[@class='title title_size_15'][1]"))
                .shouldHave(Condition.visible)
                .shouldHave(Condition.appear);
         hashMemory.put(item , element);

    }

    @И("^выполнить поиск по элементу \"([^\"]*)\"$")
    public void searcher(String item) {
        $(By.xpath("//input[@id='header-search']"))
                .shouldHave(Condition.visible)
                .shouldHave(Condition.appear)
                .sendKeys(hashMemory
                        .get(item)
                        .getText());

    }


    @И("^Наименование товара соотвествует запомненному значению \"([^\"]*)\"$")
    public void checkFindRes(String item) {
        String nameOfResult = $(By.xpath("//h1[@itemprop='name']"))
                .shouldHave(Condition.visible)
                .shouldHave(Condition.appear)
                .getText();
        if (nameOfResult == hashMemory.get(item).getText()){
            System.out.print("Название совпало");
        }else{
            System.out.print("НАзвание не совпало. Нашлось " + nameOfResult + " ; Ожидалось " + hashMemory.get(item).getText());
        }
    }



}
