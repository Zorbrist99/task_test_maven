package homework.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class DemoqaTests extends TestBase {
    @DisplayName("Отправить заявку")
    @Tag("Positive")
    @Test
    void demoqaPositivTets() {

        step("Открываем страницу", () -> {
            open("https://demoqa.com/automation-practice-form");
        });
        step("Заполняем заявку", () -> {
            $("#firstName").setValue("Sergey");
            $("#lastName").setValue("Ermolaev");
            $("#userEmail").setValue("Sergey.Ermolaev@mail.ru");
            $(".custom-control-label").shouldHave(text("Male")).click();
            $("#userNumber").setValue("9153432233");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("June");
            $(".react-datepicker__year-select").selectOption("1996");
            $(".react-datepicker__month").$(byText("18")).click();
            $("#subjectsInput").setValue("History").pressEnter();
            $("#hobbiesWrapper").$(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("ForTest.png");
            $("#currentAddress").setValue("Russia");
            $("#state").click();
            $("#react-select-3-option-2").click();
            $("#city").click();
            $("#react-select-4-option-1").click();
        });

        step("Подтверждаем данные", () -> {
            $("#submit").click();
        });

        step("Проверяем что заявка создалась и данные верны", () -> {
            $(".table-responsive").shouldBe(visible);
            $(".modal-body").shouldHave(text("Sergey Ermolaev")).
                    shouldHave(text("Sergey.Ermolaev@mail.ru")).
                    shouldHave(text("Male")).
                    shouldHave(text("9153432233")).
                    shouldHave(text("18 June,1996")).
                    shouldHave(text("History")).
                    shouldHave(text("Reading")).
                    shouldHave(text("ForTest.png")).
                    shouldHave(text("Russia")).
                    shouldHave(text("Haryana Panipat"));
        });

        step("Закрываем окно", () -> {
            $(".modal-footer").$(byText("Close")).click();
        });

    }

    @DisplayName("Отправить пустую заявку")
    @Tag("Negative")
    @Test
    void checkingEmptyForm() {
        step("Открываем страницу", () -> {
            open("https://demoqa.com/automation-practice-form");
        });

        step("Подтверждаем данные", () -> {
            $("#submit").click();
        });

        step("Проверяем что заявка не создалась", () -> {
            $(".table-responsive").shouldNotBe(visible);
        });

    }

    @DisplayName("Отправить не полную заявку")
    @Tag("Negative")
    @Test
    void allRequiredFieldsNotFilled() {
        step("Открываем страницу", () -> {
            open("https://demoqa.com/automation-practice-form");
        });

        step("Заполняем не все обязательные поля", () -> {
            $("#firstName").setValue("Sergey");
            $(".custom-control-label").shouldHave(text("Male")).click();
            $("#userNumber").setValue("9153432233");
        });

        step("Подтверждаем данные", () -> {
            $("#submit").click();
        });

        step("Проверяем что заявка не создалась", () -> {
            $(".table-responsive").shouldNotBe(visible);
        });
    }
}
