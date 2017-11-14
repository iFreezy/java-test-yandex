package yandex.steps;

import com.codeborne.selenide.*;
import com.codeborne.selenide.commands.PressEnter;
import cucumber.api.PendingException;
import cucumber.api.java.ru.И;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.fail;

public class BasicStepdefs {

    HashMap<String, String> hashMemory = new HashMap<>();

    @И("^Зайти на \"([^\"]*)\"$")
    public void goToSelectedPageByLink(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
        System.setProperty("selenide.browser", "Chrome");
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 20);
        WebDriverRunner.getWebDriver().get(url);
    }
    @И("^Ввести логин (.*)$")
    public void Login(String login) {

        $(By.xpath("//*[@name='username']"))
                .shouldBe(Condition.appear).shouldBe(Condition.visible).sendKeys(login);
    }
    @И("^Ввести пароль (.*)$")
    public void Password(String pass) {

        $(By.xpath("//*[@name='pass']"))
                .shouldBe(Condition.appear).sendKeys(pass);
    }
    @И("Вход")
    public void clickLogin(){
        $(By.xpath("//button[text()='Вход']"))
                .shouldBe(Condition.appear).click();
    }
    @И("Авторизация с логином (.*) прошла успешно$")
    public void checkLogin(String login){
        $(By.xpath("//div[text()=\"Логин: "+ login +"\"]"))
                .shouldBe(Condition.appear);
    }
    @И("Выполнить поиск процедуры \"([^\"]*)\"$")
    public void enterProcedure(String procName){
        SelenideElement stringSearch = $(By.xpath("//div[@class=' x-box-item']/child::input[@type=\"text\"]"));
                stringSearch.shouldBe(Condition.appear).shouldBe(Condition.visible).sendKeys(procName);
                stringSearch.sendKeys(Keys.RETURN);
    }
    @И("Нажать кнопку поиска процедуры")
    public void searchProcedure(){
        $(By.xpath("//td[@class=\"x-btn-mc\"]//child::button[text()=\"Искать\"]"))
                .shouldBe(Condition.appear).click();
    }
    @И("Выход")
    public void clickExit(){
        $(By.xpath("//button[text()='Выход']"))
                .shouldBe(Condition.appear).click();
    }
    @И("^выполнено ожидание в течение (\\d+) секунд$")
    public void waitDuring(int seconds) {
        Selenide.sleep(1000 * seconds);
    }

    @И("^нажата ссылка \"([^\"]*)\"$")
    public void goToLink(String linkName) {
        $(By.xpath("//*[text()='" + linkName + "']"))
                .shouldBe(Condition.appear).click();
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
        $(By.xpath("//*[text()='" + linkName + "' and contains(@class,'catalog-menu__list-item')][1]"))
                .shouldBe(Condition.appear)
                .click();
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
    public void checkResult(int count) throws Throwable {
        int countOfResults = $$(By.xpath("//h4[@class='title title_size_15']"))
                .size();
        if (countOfResults == count){
            System.out.println("Количество совпало");
        }else{
            fail("Количество не совпало. Нашлось " + countOfResults + " ; Ожидалось " + count);
        }

    }

    @И("^Запомнить первый элемент в списке \"([^\"]*)\"$")
    public void memResult(String item) {
         SelenideElement element = $(By.xpath("//h4[@class='title title_size_15'][1]"))
                .shouldHave(Condition.visible)
                .shouldHave(Condition.appear);
         hashMemory.put(item , element.getText().trim());
    }

    @И("^выполнить поиск по элементу \"([^\"]*)\"$")
    public void searcher(String item) {
        SelenideElement element = $(By.xpath("//input[@id='header-search']"));
        element.shouldHave(Condition.visible)
                .shouldHave(Condition.appear)
                .sendKeys(hashMemory
                        .get(item));
        element.sendKeys(Keys.RETURN);
    }

    @И("^Наименование товара соотвествует запомненному значению \"([^\"]*)\"$")
    public void checkFindRes(String item) throws Throwable {
        String nameOfResult = $(By.xpath("//h1[@class='title title_size_22']"))
                .shouldHave(Condition.visible)
                .shouldHave(Condition.appear)
                .getText().trim();
        if (nameOfResult.equals(hashMemory.get(item))){
            System.out.println("Название совпало");
        }else{
            fail("Название не совпало. Нашлось " + nameOfResult + " ; Ожидалось " + hashMemory.get(item));
        }
    }

    @И("^Верно отсортированы цены$")
    public void getPrice() throws Throwable {
        if (sortPrices($$(By.xpath("//*[@class='snippet-cell__price']/child::span[@class='price']")))){
            System.out.print("Цены отсортированы верно");
        }else{
            fail("Цены отсортированы неверно");
        }
    }


    private boolean sortPrices(ElementsCollection prices){
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
