package yandex.steps;

import com.codeborne.selenide.*;
import cucumber.api.java.ru.*;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import yandex.pages.YandexMarketPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;

public class BasicStepdefs {

    HashMap<String, String> hashMemory = new HashMap<>();
    YandexMarketPage yandexMarketPage = page(YandexMarketPage.class);

    @И("^выполнен переход по url \"([^\"]*)\"$")
    public void goToSelectedPageByLink(String url) {
        open(url);
    }

    @И("^нажата ссылка \"([^\"]*)\"$")
    public void goToLink(String linkName) {
        yandexMarketPage.clickOnLink(linkName);
    }

    @И("^выбрано из списка категорий товаров \"([^\"]*)\"$")
    public void choseLinkFromMenu(String linkName) {
        yandexMarketPage.goToTab(linkName);
    }

    @И("^установлена цена товара на значение \"([^\"]*)\"$")
    public void setPrice(String value) {
        yandexMarketPage.cost.shouldBe(Condition.appear).sendKeys(value);
    }

    @И("выбран производитель")
    public void setPrice(List<String> field) {
        for (String item : field) {
            if (!item.isEmpty()) {
                yandexMarketPage.checkboxChecked(item);
            }
        }
    }

    @И("^отображается \"([^\"]*)\" элементов$")
    public void checkResult(int count) throws Throwable {
        int countOfResults = yandexMarketPage.productsOnPage.size();
        Assert.assertTrue(format("Несоответствие количества элементов. Ожидаемый результат: %s, текущий результат: %s",
                count, countOfResults), countOfResults == count);
    }

    @И("^получен первый элемент в списке и сохранен в переменную \"([^\"]*)\"$")
    public void memResult(String item) throws InterruptedException {
        Thread.sleep(7000);
        hashMemory.put(item, yandexMarketPage.nameProductList.get(1)
                .shouldBe(Condition.visible)
                .getText().trim());
    }

    @И("^выполнен поиск по элементу \"([^\"]*)\"$")
    public void searcher(String item) {
        SelenideElement element = yandexMarketPage.searchString;
        element.shouldBe(Condition.appear)
                .sendKeys(hashMemory
                        .get(item));
        element.sendKeys(Keys.RETURN);
    }

    @И("^наименование товара соотвествует запомненному значению \"([^\"]*)\"$")
    public void checkFindRes(String item) throws Throwable {
        String nameOfResult = yandexMarketPage.nameProduct
                .shouldBe(Condition.visible)
                .getText().trim();
        Assert.assertTrue(format("Несоответствие названий товаров. Ожидаемый результат: %s, текущий результат: %s",
                hashMemory.get(item), nameOfResult), nameOfResult.equals(hashMemory.get(item)));
    }

    @И("выполнилась проверка сортировки цены")
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
