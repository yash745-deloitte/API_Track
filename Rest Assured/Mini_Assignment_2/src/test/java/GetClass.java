import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetClass {
    String url1 = "https://jsonplaceholder.typicode.com";

    RequestSpecification requestSpecification;

    @BeforeClass
    public void request()
    {
        requestSpecification = with().
                baseUri(url1).
                header("Content-Type","application/json");

    }

    @Test
    public void get_call()

    {
        Response response = requestSpecification.get("/posts");
        assertThat(response.statusCode(),equalTo(200));
    }

//    @Test
//    public void VerifyGetClass()
//    {
//
//
//        Response response = requestSpecification.get("/posts");
//
//        assertThat(bo);
//
//                body("userId[39]",equalTo(4))
//                .body(containsString("title"));
//    }

}
