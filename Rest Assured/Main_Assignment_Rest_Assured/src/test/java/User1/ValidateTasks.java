package User1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ValidateTasks {
    String url = "https://api-nodejs-todolist.herokuapp.com";
    String user_token;
    JSONObject jsonData;
    Logger logdata = Logger.getLogger(ValidateTasks.class);

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
    public void VerifyTasks() {
        String endpoint = "/task";
        Response response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .auth().oauth2(user_token)
                .when()
                .get(endpoint)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.getContentType(),"application/json; charset=utf-8");
        Assert.assertEquals(response.body().path("count").toString(),Integer.toString(20));
        logdata.info("Validation Of tasks Done!!");

        //Validate Task Description
        JSONArray tasks = jsonData.getJSONArray("Sheet3");
        String rbody = response.getBody().asString();
        JSONObject taskArray = new JSONObject(rbody);
        JSONArray newArray = taskArray.getJSONArray("data");
        for (int i=0;i<tasks.length();i++){
            JSONObject a_task = tasks.getJSONObject(i);
            JSONObject tsk = newArray.getJSONObject(i);
            Assert.assertEquals(tsk.get("description").toString(),a_task.get("description").toString());
            logdata.info("Description of tasks validated. !!");
        }
    }
}
