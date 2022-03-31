
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class GetClass {
    String url1 = "https://jsonplaceholder.typicode.com";

    @Test
    public void get_call()

    {
        given().
                baseUri(url1).

        when().
                get("/posts").
        then().
                contentType("application/json").
                statusCode(200);

    }

    @Test
    public void VerifyGetClass()
    {
        given().
                baseUri(url1).
                when().
                get("/posts").
                then().
                body("userId[39]",equalTo(4))
                .body(containsString("title"));
    }

}
