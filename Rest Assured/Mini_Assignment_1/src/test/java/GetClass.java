
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
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
                header("Content-Type","application/json").

        when().
                get("/posts").
        then().
                contentType("application/json").
                statusCode(200);

    }

    @Test

    public void VerifyGetClass()
    {
        Response response = given().
                baseUri(url1).
                header("Content-Type","application/json").
                when().
                get("/posts");
        JSONArray arr = new JSONArray(response.asString());
        boolean flag = false;
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject obj = arr.getJSONObject(i);
            if(obj.get("id").equals(40) &&  obj.get("userId").equals(4)){
                flag = true;
            }
        }
        Assert.assertTrue(flag);

    }

    @Test

    public void verifyGetTitle()
    {
        Response response = given().
                baseUri(url1).
                when().
                get("/posts").
                then().
                extract().response();

        JSONArray arr = new JSONArray(response.asString());

        for(int i=0; i<arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Assert.assertNotNull(obj.get("title"));
            Object data = obj.get("title");
            Assert.assertTrue(data instanceof String);
        }
    }


}

