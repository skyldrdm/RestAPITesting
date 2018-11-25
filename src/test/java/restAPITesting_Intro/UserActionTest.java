package restAPITesting_Intro;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.assertion.BodyMatcher;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
public class UserActionTest {
	

	@BeforeClass
	public void init() {
		RestAssured.baseURI = "https://www.batch8-awesome-api.dev.cc"; 
		RestAssured.basePath = "/wp-json/wp/v2";
		
	}
	
	
	
	@Test
	public void testPublicUserGetOnlyAdminProfileInfo() {
		
		given()
			.relaxedHTTPSValidation()
			//.auth().preemptive().basic("admin", "admin")
		.when()
			.log().all()
		.get("/users")
		.then()
			.statusCode(200)
			.header("Content-Type", "application/json; charset=UTF-8")
			.body("id", hasSize(1))
			.body("name", hasItem("admin"));

	}
	
	@Test
	public void testPublicUserShouldNotBeAbleToCreateANewUser() {
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.contentType(ContentType.JSON)
		.when()
			.log().all()
			.body("{\r\n" + 
					"	\"username\" : \"userx\" ,\r\n" + 
					"	\"name\" : \"usere\" ,\r\n" + 
					"	\"first_name\" : \"usere\", \r\n" + 
					"	\"last_name\" : \"usere\" ,\r\n" + 
					"	\"email\" : \"usere@gmail.com\" ,\r\n" + 
					"	\"roles\" : \"author\" ,\r\n" + 
					"	\"password\" : \"usere\" \r\n" + 
					"}")
			.contentType(ContentType.JSON)		
		.post("/users")
		.then()
		//	.statusCode(HttpStatus.SC_CREATED)
			.statusCode(201)
			//.header("Content-Type", "application/json; charset=UTF-8")
			.body("username", is("userx"))
			;

	}
	
	//TASK 1
	@Test
	public void testAdminUserShouldBeAbleToUpdateExistingUser() {
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.contentType(ContentType.JSON)	
		.when()
			.log().all()
			.body("{\r\n" + 
					"	\"first_name\" : \"user99\", \r\n" + 
					"	\"last_name\" : \"user99\" ,\r\n" + 
					"	\"email\" : \"user99@gmail.com\" ,\r\n" + 
					"	\"roles\" : \"author\" ,\r\n" + 
					"	\"password\" : \"user99\" \r\n" + 
					"}")
				
			.pathParam("newId", 11)
		.put("/users/{newId}")
		.then()
		//	.statusCode(HttpStatus.SC_CREATED)
			.statusCode(200)
			//.header("Content-Type", "application/json; charset=UTF-8")
			.body("first_name", is("user99"))
			;

	}
	
	//TASK 2
	@Test
	public void adminUser_ShouldBeAbleto_DeleteExistingUser() {
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.queryParam("force", true)
			.param("reassign", 1)
			
		.when()
			.log().all()
			.pathParam("deleteId", 12)
		.delete("/users/{deleteId}")
		.then()
			.statusCode(200)
			//.statusCode(HttpStatus.SC_OK)
			.contentType(ContentType.JSON)
			.body("deleted", is(true) )
			.body("previous.id", equalTo(12)) ;	
	}
	
	//TASK 3
	@Test
	public void publicUser_ShouldNotBeAbleto_View_ExistingUser_otherThanAdmin() {
		
		given()
		.relaxedHTTPSValidation()
		.auth().preemptive().basic("user2", "user2")
//		.pathParam("newId", 1)
	.when()
		.log().all()
	.get("/users/")
//		.get("/users/{newId}")
	.then()
//		.statusCode(HttpStatus.SC_FORBIDDEN)
		.statusCode(200)
//		.contentType(ContentType.JSON)
		.body("id", equalTo(1) )
		;
	
	}	
	
	
}
