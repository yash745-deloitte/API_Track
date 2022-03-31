import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class PutClass {

    String url2 = "https://reqres.in";
    File inputdatajson = new File("src//test//resources//data.json");
    RequestSpecification request;
    ResponseSpecification response;

    @BeforeMethod
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri(url2).addHeader("Content-Type", "application/json");
        request = RestAssured.with().spec(reqBuilder.build());
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON);
        response = specBuilder.build();
    }

    @Test
    public void PutClassVerify() {
        given().
                baseUri(url2).
                body(inputdatajson).
                when().
                put("/api/users").
                then().statusCode(200).headers("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    public void PutClassValidation() {
        given().
                baseUri(url2).
                header("Content-Type", "application/json").
                body(inputdatajson).
                when().
                put("/api/users").
                then().
                body("name",equalTo("Arun"),"job",equalTo("Manager"));
    }
}