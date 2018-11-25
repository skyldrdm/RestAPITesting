package autherization_api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAth2 {

	Faker faker = new Faker();
	int newUserID;
	String token = "10960~I8yk3E1iVPYyMtxeNQF7p41eXy8uBfyymDyWIHF2QBpqU9UjEHHXvEkQQfUt4W71";
		
	@BeforeClass
	public void init() {

		RestAssured.baseURI = "https://learn.cybertekschool.com";
		RestAssured.basePath = "/api/v1";
		RestAssured.useRelaxedHTTPSValidation();
		
	}
	
	@Test
	public void getAllModulesFromCanvas() {

		
		given()
			.accept(ContentType.JSON)
			.auth().oauth2(token)
		.when()
			.log().all()
			.get("/courses/73/modules")
		.then()
			//.statusCode(HttpStatus.SC_OK)
			.statusCode(200)
			.body("$", hasSize(9));
		
		//	https://www.banking-application.dev.cc/wp-json/jwt-auth/v1/token
		
	}
	
	
	//@Test
	  public void test() {
		RestAssured.basePath = "/wp-json/wp/v2";
		
	    Map<String,Object> mp = new HashMap<>() ; 
	    mp.put("title", faker.book().title());
	    mp.put("content", "super");
	    mp.put("status", "publish");

	    given()
	    .headers("Authorization", "Bearer " +token)
	    .contentType(ContentType.JSON)
	    .body(mp).
	  when()
	    .log().all()
	    .post("/posts").
	  then()
	    //.statusCode(HttpStatus.SC_CREATED)
	    .statusCode(201)
	    .header("Content-Type" , containsString("application/json") )
	    ;

	    
	  }

}
