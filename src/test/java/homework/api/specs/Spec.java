package homework.api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;

public class Spec {
    public static RequestSpecification requestSpecification = with()
            .log().uri()
            .log().parameters();

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(BODY)
            .build();
}
