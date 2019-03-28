package steps;

import com.fasterxml.jackson.databind.JsonNode;
import driver.DriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FactoryPatternTest {

    DriverManager driverManager;
    WebDriver driver;
    private String auth_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjFlNGU4Zjg1LWI3MTUtNDA3Ni1iNzI4LTU3NzRkZmQ5NDk3ZSIsImlhdCI6MTU1MzU5NzYwMiwiZXhwIjoxNTUzNjg0MDAyfQ.wQqU6ks5-bXp9oDmvPOe2d8KMjm8K9f0U66Bs1m5gTGvkpDNs_LPCpAjmdux0oAZZ0oI93lfbRPmoPZQIQC_nQ";
    private String url = "http://vacation-srv.chisw.us:3000/";

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }


    @Test
    public void launchGoogleTest() {
        //
        RestAssured.baseURI = url ;
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("email", "ann.prokoliy@chisw.com");
        jsonAsMap.put("password", "Aprokoliy_123");

        Response response =
                (Response) given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + auth_token)
                        .body(jsonAsMap)
                        .when()
                        .post("auth/signin")
                        .thenReturn().body();
        JsonNode mainNode = response.body().jsonPath().getObject("", JsonNode.class);
        String token = mainNode.get("data").get("token").asText();
        System.out.println(token);



    }

    @Test
    public void launchYahooTest() {
        driver.get("https://www.yahoo.com");
        Assert.assertEquals("Yahoo", driver.getTitle());
    }

}
