import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetClass {

    String url1 = "https://jsonplaceholder.typicode.com";
    RequestSpecification request;
    ResponseSpecification response;


    @BeforeMethod
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri(url1).addHeader("Content-Type", "application/json");
        request = RestAssured.with().spec(reqBuilder.build());
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON);
        response = specBuilder.build();
    }

    @Test
    public void Get_Validation() {
        given().
                spec(request).
                when().
                get("/posts").
                then().
                spec(response).statusCode(200).headers("Content-Type", "application/json; charset=utf-8");
    }


    @Test
    public void VerifyIdUserId() {
        Response response = given().spec(request).when().get("/posts").then().extract().response();
        JSONArray arr = new JSONArray(response.asString());
        boolean flag = false;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if (obj.get("id").equals(40) && obj.get("userId").equals(4)) {
                flag = true;
            }
        }
        Assert.assertTrue(flag);
    }


    @Test
    public void VerifyTitleString() {
        Response response = given().spec(request).when().get("/posts").then().extract().response();
        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Assert.assertNotNull(obj.get("title"));
            Object data = obj.get("title");
            Assert.assertTrue(data instanceof String);
        }
    }
}
