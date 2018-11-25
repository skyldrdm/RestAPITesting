package restAPITesting;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPath_GoT {
	
	@BeforeClass
	public void init() {

		RestAssured.baseURI = "https://api.got.show/api/characters/";
		RestAssured.basePath = "/wp-json/wp/v2";
		RestAssured.useRelaxedHTTPSValidation();
	}
	
	@Test
	public void testJsonPath() {
		
		  Response response = given().relaxedHTTPSValidation().when().log().all().get("https://api.got.show/api/characters/");
	
		  String responseString = response.asString();
		  System.out.println(responseString);
		  		  
		  JsonPath jsonPath = response.jsonPath();
		  JsonPath jp = JsonPath.from(responseString);
		  
		  System.out.println(jp.getString("house[0]"));
		  List<String> starks = jp.getList("findAll{it.house=='House Stark'}.name");
		  
		  System.out.println(starks);
		  
		  
		  List<Object> list = jsonPath.get("findAll{it.house=='House Stark'}");
		  System.out.println(list);
		  
		  System.out.println(list.size());  
	}
	
	
	

}
