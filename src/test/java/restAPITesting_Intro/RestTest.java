package restAPITesting_Intro;

import org.testng.annotations.BeforeMethod;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class RestTest {
	
	//@Ignore
	@BeforeClass
	public void setUp() {
		RestAssured.baseURI = "https://www.kaan-api-site.dev.cc/wp-admin/edit.php/wp-json";
		basePath = "/wp/v2";
	}

	@Ignore
	@Test
	public void firstTest() {
		when()
		.get("http://73.166.37.2:1000/ords/hr/employees/100")
		.then()
		.statusCode(200);
	}

	@Test
	public void secondTest() {
		given().relaxedHTTPSValidation()  //bypass ssl validation
		.when()
		.get("https://www.kaan-api-site.dev.cc/wp-admin/edit.php/wp-json/wp/v2/posts/")
		.then()
		.statusCode(200);
	}

	@Test
	public void idTest() {
		given().relaxedHTTPSValidation()
		.when()
		//.log().all()
		.get("https://www.kaan-api-site.dev.cc/wp-json/wp/v2/posts/23")
		.then()
		.statusCode(200)
		.and()
		//.assertThat()
		.body("id", equalTo(23))
		.body("title.rendered", equalTo("Merhaba Kaan"));
	}

	@Test
	public void statusTest() {
		given().relaxedHTTPSValidation()
		.when()
		.log().all()
		.get("https://www.kaan-api-site.dev.cc/wp-json/wp/v2/posts/{id}", 23)
		.then().statusCode(200)
		.and()
		.body("status", equalTo("publish"))
		.body("type", equalTo("post"))
		.body("content.protected", equalTo(false));
	}
	
	
	@Test
	public void baseURITest() {
		// we can put baseURI and basePath inside @BeforeClass
		//RestAssured.baseURI = "https://www.batch8-awesome-api.dev.cc/wp-json";
		//RestAssured.basePath = "/wp/v2";
		// basePath = "/wp/v2";
		given()
		.relaxedHTTPSValidation()
		.when()
		//.log().all()
		.then()
		//.log().all()
		.statusCode(200)
		.and().body("status", equalTo("publish"))
		.body("type", equalTo("post"));
	}
}
