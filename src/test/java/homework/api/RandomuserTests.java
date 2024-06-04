package homework.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static homework.api.specs.Spec.requestSpecification;
import static homework.api.specs.Spec.responseSpecification;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RandomuserTests {
    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://randomuser.me/api/";
    }


    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать случайного пользователя")
    void generateRandomUser() {
        Response response = given(requestSpecification)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("results[0].name")). isNotNull();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать нескольких случайных пользователей")
    void generateMultipleRandomUsers() {
        int numberOfUsers = 3;

        Response response = given(requestSpecification)
                .queryParam("results", numberOfUsers)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getList("results").size()). isEqualTo(numberOfUsers);
        assertThat(response.jsonPath().getInt("info.results")). isEqualTo(numberOfUsers);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать случайного пользователя опрeделённой национальности")
    void generateRandomUserCertainNationality() {
        String nationality = "DE";

        Response response = given(requestSpecification)
                .queryParam("nat", nationality)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("results[0].nat")). isEqualTo(nationality);

    }

    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать случайного пользователя, ответ в формате XML")
    void generateRandomUserResponseXMLFormat() {
        int numberOfUsers = 3;
        String nationality = "DE";
        String format = "xml";

        Response response = given(requestSpecification)
                .queryParam("results", numberOfUsers)
                .queryParam("nat", nationality)
                .queryParam("format", format)
                .header("Accept", "application/xml")

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        try (FileOutputStream fos = new FileOutputStream("random_users.xml")) {
            fos.write(response.getBody().asByteArray());
            System.out.println("XML файл сохранен успешно.");
        } catch (IOException e) {
            System.out.println(e);
        }


        assertThat(response.getBody().asString()).isNotEmpty();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать случайного пользователя с конкретными данными в ответе")
    void generateRandomUserWithSpecificDataInResponse() {

        String fields = "name, location";
        Response response = given(requestSpecification)
                .queryParam("inc", fields)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("results[0].name")). isNotNull();
        assertThat(response.jsonPath().getString("results[0].location")). isNotNull();
        assertThat(response.jsonPath().getString("results[0].email")). isNull();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Сгенерировать пароль в определенном формате [A-Za-z0-9]{1,16}")
    void generatePasswordInSpecificFormat() {
        Response response = given(requestSpecification)
                .queryParam("password", "upper,lower,1-16")

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("info.seed")). isNotNull();
        assertThat(response.jsonPath().getString("results[0].login.password"))
                .containsPattern("[A-Za-z0-9]{1,16}");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Отправить запрос с невалидным количеством пользователей")
    void sendRequestWithInvalidNumberUsers() {
        int newValue = Integer.MAX_VALUE + 1;
        Response response = given(requestSpecification)
                .queryParam("results", newValue)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("info.results")). isNotNull();
    }

    @Test
    @Tag("Negative")
    @DisplayName("Отправить запрос с невалидным полом")
    void sendRequestWithInvalidGender() {
        String invalidGender = "Other";
        Response response = given(requestSpecification)
                .queryParam("gender", invalidGender)

                .when()
                .get()

                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getList("results.gender"))
                .allMatch(gender -> gender.equals("male") || gender.equals("female"));
    }
}
