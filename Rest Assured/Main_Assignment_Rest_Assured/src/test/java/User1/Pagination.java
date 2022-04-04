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
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Pagination {
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
    public void pagiNation(){
        List<Integer> list= Arrays.asList(2, 5, 10);
        String endpoint = "/task";
        for(int x : list) {
            Response response = given()
                    .baseUri(url)
                    .contentType(ContentType.JSON)
                    .auth().oauth2(user_token)
                    .param("limit", x)
                    .when()
                    .get(endpoint)
                    .then().extract().response();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
            Assert.assertEquals(response.body().path("count").toString(), Integer.toString(x));
        }
    }
}
