package yandex.pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchPage {
    public GoogleResultsPage search(String query) {
        $(By.linkText("q")).setValue(query).pressEnter();
        return page(GoogleResultsPage.class);
    }
}

class GoogleResultsPage {
    public ElementsCollection results() {
        return $$("#ires li.g");
    }
}