package files;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;


public class jiraTest {

	public static void main(String[] args) {
			RestAssured.baseURI="http://localhost:8080";
			
			// authen
			SessionFilter session = new SessionFilter();
		String response =	given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"hieunguyen2818\", \"password\": \"159951\" }")
			.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().asString();
			
			String expectedMessage = "Hi, how are you?";
			//add comment
			String addCommentResponse = given().pathParam("key", "10001").log().all().header("Content-Type","application/json").body("{\r\n"
					+ "    \"body\": \""+expectedMessage+"\"\r\n"
					+ "}")
			.filter(session).when().post("/rest/api/2/issue/{key}/comment")
			.then().log().all().extract().response().asString();
		//lấy ra comment ID của comment vừa add
			JsonPath js = new JsonPath(addCommentResponse);
			String commentIDs = js.getString("id");
			
			//Get issue
		String issueDetails = 	given().filter(session).pathParam("key", "10001").queryParam("fields", "comment")
			.log().all().when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println("---------------");
		System.out.println(issueDetails);
		
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.getInt("fields.comment.comments.size()");
		for(int i = 0; i<commentCount; i++) {
			String commentIDIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIDIssue.equalsIgnoreCase(commentIDs)) {
				String message = js1.getString("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
	} 

}
