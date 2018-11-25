package autherization_api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class OAth3 {

	Faker faker = new Faker();
	int newUserID;
	String token = "10960~I8yk3E1iVPYyMtxeNQF7p41eXy8uBfyymDyWIHF2QBpqU9UjEHHXvEkQQfUt4W71";
	RequestSpecification req;
	Response res;
	
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
	
	
	@Test(priority=1)
	public void givenUserTest() {
		req = given()
		.accept(ContentType.JSON)
		.auth().oauth2(token);
		
	}
	
	@Test(priority=2)
	public void whenUserTest() {
		res = req.when()
		.log().all()
		.get("/courses/73/modules");
		
	}
	
	@Test(priority=3)
	public void thenUserTest() {
		ValidatableResponse valRes= res.then()		
		.statusCode(200)
		.body("$", hasSize(9));

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
