package User1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ValidateUserDetails {
    String url = "https://api-nodejs-todolist.herokuapp.com";
    String user_token;
    JSONObject jsonData;

    @BeforeSuite
    public void setup() throws IOException {
        DataEntry imp =  new DataEntry();
        jsonData = imp.DataEntry();

        //Create Login Data
        JSONArray loginData = jsonData.getJSONArray("Sheet2");
        JSONObject login = loginData.getJSONObject(0);
        File file2 = new File("src/test/resources/login.json");
        file2.createNewFile();
        FileWriter fileWriter2 = new FileWriter(file2);
        fileWriter2.write(login.toString());
        fileWriter2.flush();
        fileWriter2.close();
    }

    @Test(priority = 1)
    public void loginUser() {
        File login = new File("src\\test\\resources\\login.json");
        String endpoint = "/user/login";
        Response response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .when()
                .body(login)
                .post(endpoint)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.getContentType(),"application/json; charset=utf-8");
        user_token = response.body().path("token");
    }

    @Test(priority = 2)
    public void validate_details() {
        JSONArray userData = jsonData.getJSONArray("Sheet1");
        JSONObject user = userData.getJSONObject(0);
        String endpoint = "/user/me";

        Response response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .auth().oauth2(user_token)
                .when()
                .get(endpoint)
                .then().extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
        Assert.assertEquals(user.get("email"), response.body().path("email"));
        Assert.assertEquals(user.get("name"), response.body().path("name"));
        Assert.assertEquals(Integer.toString(user.getInt("age")) ,Integer.toString(response.body().path("age")));
    }
}
