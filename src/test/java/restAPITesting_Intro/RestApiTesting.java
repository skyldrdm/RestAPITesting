package restAPITesting_Intro;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestApiTesting {

	@BeforeClass
	public void setUp() {
		RestAssured.baseURI = "https://www.kaan-api-site.dev.cc/wp-json";
		basePath = "/wp/v2";
	}
	@Test
	public void testWithHamcrest() {
		int a = 5, b = 5, c = 6;
		assertTrue(a == b);
//		assertEquals(a, c);
		assertThat(a, equalTo(b));
		assertThat("abc", is("abc"));
//		assertThat("abc", not(equalTo("abc")));
		assertThat("abc", containsString("a"));
		assertThat("aBC", equalToIgnoringCase("abc"));
		assertThat(5, greaterThan(4));
		assertThat(4, lessThan(5));
		assertThat(5, lessThanOrEqualTo(5));

		List<Integer> lst = Arrays.asList(2, 3, 4, 5);
		assertThat(lst, hasSize(4));
		//assertThat(lst, contains(2, 3, 4));
		assertThat(lst, everyItem(lessThan(10)));
	}
	
	@Test
	public void simpleGetRequest() {
		given()
			.relaxedHTTPSValidation()
			.when().log().all()
			//.queryParam("per_page", 1).get("/posts/{id}", 23)
				.pathParam("whatever", 23)
			//.get("/posts/{id}")
				.get("/posts/{whatever}")
			.then()
				.log().all()
			.assertThat().statusCode(200)
				.and()
			.body("id", equalTo(23))
			.body("title.rendered", is("Merhaba Kaan"))
			.body("sticky", is(false));
	}
	
	@Test
	public void printAllTheBodyRequest() {
		given()
			.relaxedHTTPSValidation().when().log().all()
		.get("/posts")
			.body()
		.prettyPrint();
	}
	
	@Test
	public void simplePostTest() {
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.contentType(ContentType.JSON)
		.when()
			.body("{\r\n" + 
				"	\"title\" : \"Bootcapm DAY 4 POST\",\r\n" + 
				"	\"content\" : \"Happy offers\",\r\n" + 
				"	\"status\": \"publish\"\r\n" + 
				"}")
			.log().all()
		.post("/posts")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void simplePutTest() {
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.contentType(ContentType.JSON)
		.when()
			.body("{\r\n" + 
				"	\"title\" : \"BOOTCAMP DAY 4 Update\",\r\n" + 
				"	\"content\" : \"STEEL in the market!!!\",\r\n" + 
				"	\"status\": \"publish\"\r\n" + 
				"}")
			.log().all()
			.pathParam("newId", 47)
			.put("/posts/{newId}")
		.then()
			.statusCode(200)
			.body("title.rendered", is("BOOTCAMP DAY 4 Update"));
	}
	
	@Test
	public void simpleDeleteTest() {
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
		.when()
			.log().all()
			.pathParam("deleteId", 60)
			//.queryParam("force", true) //only for word press
			.delete("/posts/{deleteId}")
		.then()
			.statusCode(200);			
	}
}
