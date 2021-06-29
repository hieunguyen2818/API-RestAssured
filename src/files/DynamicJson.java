package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	
	@Test(dataProvider="bookData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().body(payload.addBook(isbn,aisle))
				.when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = reUsableMethods.rawToString(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="bookData")
	public Object[][] getData() {
		return new Object[][] {{"math","1234"},{"novel","2345"},{"ASP tool","7890"}};
	}
	
	public String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));		
	}
	
	public void addBookByFiles(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().body(payload.addBook(isbn,aisle))
				.when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = reUsableMethods.rawToString(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
}
