package User1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUser {
    String url = "https://api-nodejs-todolist.herokuapp.com";
    String user_token;
    JSONObject jsonData;

    Logger logdata = Logger.getLogger(CreateUser.class);

    @BeforeClass
    public void get_data() throws IOException {
        DataEntry imp =  new DataEntry();
        jsonData = imp.DataEntry();

        //Create Registration Data
        JSONArray userData = jsonData.getJSONArray("Sheet1");
        JSONObject user = userData.getJSONObject(0);
        File file=new File("src/test/resources/user.json");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(user.toString());
        fileWriter.flush();
        fileWriter.close();
        logdata.info("User Created !!");

    }

    @Test(priority = 1)
    public void user_register() {
        File user = new File("src\\test\\resources\\user.json");
        String endpoint = "/user/register";
        Response response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(endpoint)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(),201);
        Assert.assertEquals(response.getContentType(),"application/json; charset=utf-8");
        user_token = response.path("token");
    }
}