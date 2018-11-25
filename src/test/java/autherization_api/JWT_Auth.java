package autherization_api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JWT_Auth {

	Faker faker = new Faker();
	int newUserID;
	String token;
		
	@BeforeClass
	public void init() {

		RestAssured.baseURI = "https://www.banking-application.dev.cc";
		RestAssured.basePath = "/wp-json/wp/v2";
		RestAssured.useRelaxedHTTPSValidation();
		
		token= getToken();
	}
	
	@Test
	public void getTokenKey() {
		RestAssured.basePath = "/wp-json/jwt-auth/v1";
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.URLENC)
			.formParam("username", "admin")
			.formParam("password", "admin")
		.when()
			.post("/token")
		.then().log().all()
			//.statusCode(HttpStatus.SC_OK)
			.statusCode(200);
		
		//	https://www.banking-application.dev.cc/wp-json/jwt-auth/v1/token
		
	}
	
	public String getToken() {
		RestAssured.basePath = "/wp-json/jwt-auth/v1";
		
		Response response = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.URLENC)
			.formParam("username", "admin")
			.formParam("password", "admin")
		.when()
			.post("/token");
		
		token = response.jsonPath().getString("token");

		return token;
	}
	
	@Test
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
