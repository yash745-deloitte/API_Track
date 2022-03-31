import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class PutClass {
    String url2 = "https://reqres.in/api";

    @Test
    public void put_call()
    {
        File jsonputdata = new File("src//test//resources//putcall.json");
        given().
                baseUri(url2).
                body(jsonputdata).

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
        when().
              get("/users").
        then()
              .body("name",equalTo("Arjun"))
              .body("job",equalTo("Manager"));
    }
}
