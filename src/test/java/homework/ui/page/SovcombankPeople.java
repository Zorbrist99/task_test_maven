package homework.ui.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class SovcombankPeople {
    private final SelenideElement jobButtonClik = $("#main-menu"),
            listCities = $(".v-input__append-inner", 1),
            chooseCity = $("#list-item-981-0"),
            listCompanies = $(".v-input__append-inner", 2),
            chooseCompany = $(".v-list-item__title", 5),
            jobListing = $(".section-vacancies");


    @Step("Открыть страницу")
    public SovcombankPeople openPage() {
        open("https://people.sovcombank.ru/");
        return this;
    }

    @Step("Нажать на кнопку Вакансии")
    public SovcombankPeople lickOnVacanciesButton() {
        jobButtonClik.$(byText("Вакансии")).click();
        return this;
    }

    @Step("Выбрать город Москва")
    public SovcombankPeople chooseCity() {
        listCities.click();
        chooseCity.click();
        return this;
    }

    @Step("Выбрать компанию Совкомбанк Технологии")
    public SovcombankPeople chooseCompany() {
        listCompanies.click();
        chooseCompany.click();
        return this;
    }

    @Step("Проверить отображение списка")
    public SovcombankPeople checkList() {
        sleep(1000);
        jobListing.shouldBe(visible);
        return this;
    }

    @Step("Собрать результаты в коллекцию")
    public ElementsCollection elementsCollection() {
        ElementsCollection results = $$(".section-vacancies.full-width");
        return results;
    }

    @Step("Проверить атрибуты: Город и Компанию")
    public SovcombankPeople checkAttributes(ElementsCollection results) {
        for (int i = 0; i < results.size(); i++) {
            String newValue = results.get(i).getText();
            if ((newValue.contains("Москва") || newValue.contains("Вся Россия")) && newValue.contains("Совкомбанк Технологии")) {
                System.out.println("Вакансия соответствует условиям: " + newValue);
            } else {
                System.out.println("Вакансия НЕ соответствует условиям: " + newValue);
            }
        }
        return this;
    }
}
