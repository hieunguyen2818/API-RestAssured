package files;

import io.restassured.path.json.JsonPath;

public class reUsableMethods {
	public static JsonPath rawToString(String response) {
		JsonPath js1 = new JsonPath(response);
		return js1;
	}
}
