package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {

	public static void main(String[] args) throws IOException {
//	 given : all the input details
		// when: submit api
		// then: validate api test 

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//addPlace
	String response = 	given().log().all().queryParam("key", "qaclick123").body(generateStringFromResource("C:\\Users\\WELCOME\\Desktop\\body.json")).when().post("maps/api/place/add/json").then().assertThat()
		.statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		String newAdress = "63 xuan thuy, HN, VietNam";
	
		//update Place
		given().log().all().queryParam("key", "qaclick123").body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAdress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json").then().assertThat().log().all().body("msg", equalTo("Address successfully updated"));
		
		//get place
		
		String getPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();
		
		String actualAddress = reUsableMethods.rawToString(getPlaceResponse).getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAdress);
		
	}

	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));		
	}
}
