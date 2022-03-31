import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class PutClass {
    String url2 = "https://reqres.in/api";
    File jsonputdata = new File("src//test//resources//putcall.json");

    @Test
    public void put_call()
    {

        given().
                baseUri(url2).
                body(jsonputdata).
                header("Content-Type","application/json").

        when().
                put("/users").

        then().
                contentType("application/json").
                statusCode(200);
    }
    @Test
    public void VerifyPutClass()
    {
        given().
              baseUri(url2).
              body(jsonputdata).
              header("Content-Type","application/json").
        when().
              put("/users").
        then().
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));
    }
}
