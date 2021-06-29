package testcases;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(payload.coursePrice());
		int numberOfCourses = js.getInt("courses.size()");
		
		System.out.println(numberOfCourses);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		String titleOfFirstCourse = js.getString("courses[0].title");
		System.out.println(titleOfFirstCourse);
		
		System.out.println("-------------");
		for(int i = 0; i<numberOfCourses; i++) {
			System.out.println(js.getString("courses["+i+"].title").toString());
			System.out.println(js.getString("courses["+i+"].price").toString());
			
			System.out.println("----number Of Copies: -----");	
			for(int j = 0; j<numberOfCourses; j++) {
			String titles = js.getString("courses["+j+"].title");
			if(titles.equals("RPA")) {
				System.out.println(js.getString("courses["+j+"].copies"));
			}
			
		}
	}
	}
}

