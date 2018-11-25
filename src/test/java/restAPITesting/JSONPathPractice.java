package restAPITesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JSONPathPractice {

	Faker faker = new Faker();
	int newUserID;

	@BeforeClass
	public void init() {

		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc";
		RestAssured.basePath = "/wp-json/wp/v2";

	}

	@Test
	public void testJSONPath() {
		Response response = given().relaxedHTTPSValidation().when().log().all().get("/users/{id}", 1);

		System.out.println("BEGINNING" + response.asString() + "END");
		response.prettyPrint();
		JsonPath jsonPath = response.jsonPath();
		System.out.println(" JASSON" + jsonPath);
	
	}
	
	@Test
	public void testJSONPath2() {
		Response response = given().relaxedHTTPSValidation().when().log().all().get("/users/{id}", 1);

		System.out.println(response.asString());
		
		response.prettyPrint();
		JsonPath jsonPath = response.jsonPath();
		System.out.println(jsonPath.getString("title.rendered"));
		System.out.println(jsonPath.getString("slug"));
		System.out.println(jsonPath.getString("_links.self[0].href"));

	}
	
	@Test
	public void driverInfoJSONPath() {
		Response response = given().relaxedHTTPSValidation().when().log().all()
				.get("http://ergast.com/api/f1/drivers.json");

		JsonPath jsonPath = response.jsonPath();
		System.out.println(jsonPath.getString("MRData.DriverTable.Drivers[1].givenName"));

		String str = jsonPath.setRoot("MRData.DriverTable.Drivers[1]").get("givenName");
		
		//String str = jsonPath.getString("MRData.DriverTable.Drivers[1].givenName");
		
		
		assertThat(str, equalToIgnoringCase("george"));
		assertThat(str, equalTo("George"));

	}
	
	
	

}