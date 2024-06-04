package homework.ui;

import com.codeborne.selenide.ElementsCollection;
import homework.ui.page.SovcombankPeople;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PeopleSovcombankTests extends TestBase {
    SovcombankPeople sovcombankPeople = new SovcombankPeople();

    @Test
    @DisplayName("Спарсить вакансии с сайта")
    void parseDataFromSite() {
        sovcombankPeople.openPage()
                .lickOnVacanciesButton()
                .chooseCity()
                .chooseCompany()
                .checkList()
                .checkList();
        ElementsCollection results = sovcombankPeople.elementsCollection();
        sovcombankPeople.checkAttributes(results);
    }
}
