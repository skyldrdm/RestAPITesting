package restAPITesting;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JSON {
///&APPID=6e14338fb0279331c26170d93f7cde45
	  @BeforeClass
	  public void LB() {
	    
	    RestAssured.baseURI = "api.openweathermap.org" ; 
	    RestAssured.basePath = "data/?q=London" ; 
	    RestAssured.useRelaxedHTTPSValidation(); 
	  }
	
	  @Test
	  public void simpleGetRequest() {
	    
	    given()
	      //.relaxedHTTPSValidation()
	    .when()
	      .log().all()
	      //.queryParam("per_page",2)
	      .get("/posts")
	    .then()
	      .log().all() 
	      .assertThat()
	      .statusCode(200)
	      ;
	  }
	  
	  @Test
	  public void getFirstAuthor() {
	  
	  Response response = given().relaxedHTTPSValidation().when().log().all().get("/posts");
	  
	  
	  
//	  System.out.println(response.asString());
//		response.prettyPrint();
		JsonPath jsonPath = response.jsonPath();

		System.out.println(jsonPath.getString("[0].author"));
		//System.out.println(jsonPath.getString("author[0]"));
		
		List<Object> lst = jsonPath.getList("author");
		List<Integer> lstNum = jsonPath.getList("author", Integer.class);
		
		System.out.println("lst: " + lst);
		System.out.println("lstNum: " + lstNum);
		
		// _links.version-history.count
		
		
		
		System.out.println(jsonPath.getList("_links.version-history[0].count"));
		
		List<Object> lstCount = jsonPath.getList("_links.version-history.count");
		
		System.out.println(lstCount);
		
	  }
	  
	  @Test
	  public void getErgastGivenName() {
		  
		  Response response = given().relaxedHTTPSValidation().when().log().all().get("http://ergast.com/api/f1/drivers.json");
		  JsonPath jsonPath = response.jsonPath();
		  
		  List<String> lstGivenName = jsonPath.getList("MRData.DriverTable.Drivers.givenName", String.class);
			
		  System.out.println(lstGivenName);
		  System.out.println(lstGivenName.size());
		  
		  // Get first driver
		  jsonPath.getString("MRData.DriverTable.Drivers[0]");
		  
		  Map map1 = jsonPath.getMap("MRData.DriverTable.Drivers[0]");
		  System.out.println(map1);
		  System.out.println(map1.keySet());
		  
		  Map <String, String> map2 = jsonPath.getMap("MRData.DriverTable.Drivers[0]", String.class, String.class);
		  
		  System.out.println(map2.values());
		  
		  System.out.println(jsonPath.getString("MRData.DriverTable.Drivers[1].givenName"));
		  
		  // JSONPATH that restAssured use is the GPath from groovy
		  // Predicate
		  
		  List<Object> list = jsonPath.get("MRData.DriverTable.Drivers.findAll{it.givenName=='George'}");
		  
		  System.out.println(list);
		  
		  List<Object> list2 =  jsonPath.get(
				  "MRData.DriverTable.Drivers.findAll{it.givenName=='George' && it.nationality=='American'}") ;
		  
		  System.out.println(list2);
		  
		  
		  // find out all the drivers that has givenName length is equal to 3:
		  List<Object> list3 = 	jsonPath.get(
				  "MRData.DriverTable.Drivers.findAll{it.givenName.length()==3}");
		  System.out.println(list3);
		  
		  List<Object> list4 = 	jsonPath.get(
				  "MRData.DriverTable.Drivers.findAll{it ->it.givenName.size()==3}.familyName");
		  System.out.println("======");
		  System.out.println(list4);
		  System.out.println("******");
		  
		  // single JSON object --> Driver object in Java
		  // Data binding --> Binding JSON field to POJO field
		  
		  DriverPojo driverObj = jsonPath.getObject("MRData.DriverTable.Drivers[1]", DriverPojo.class);
		  System.out.println(driverObj);
		  
		  
	  }
	  
	  
	  
	  
	  
	  
}
