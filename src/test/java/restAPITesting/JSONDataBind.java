package restAPITesting;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;

public class JSONDataBind {

	@Test
	public void dataBindTest() throws Exception {

		String jsonString = "{\"name\": \"Adam\", \"age\":21}";

		String jsonString2 = "{\"driverId\": \"abate\",\r\n"
				+ "              \"url\": \"http://en.wikipedia.org/wiki/Carlo_Mario_Abate\",\r\n"
				+ "              \"givenName\": \"Carlo\",\r\n" + "              \"familyName\": \"Abate\",\r\n"
				+ "              \"dateOfBirth\": \"1932-07-10\",\r\n"
				+ "              \"nationality\": \"Italian\"\r\n" + "               }";

		ObjectMapper om = new ObjectMapper();
		Person obj = om.readValue(jsonString, Person.class);

		System.out.println(jsonString);
		System.out.println(obj);

		String convertedStr = om.writeValueAsString(obj);
		System.out.println(convertedStr);

		// serialize: convert an object into that string,
		// deserialize: convert a string into that object ,

		ObjectMapper om2 = new ObjectMapper();
		Person obj2 = om.readValue(jsonString2, Person.class);
		System.out.println(obj2);

	}

	@Test
	public void databindTestWithCollection() throws Exception {

		String jsonStringArr = "[ {\"name\":\"Adam\", \"age\":10} , {\"name\":\"john\", \"age\":12} , {\"name\":\"yuAN\", \"age\":25} ] ";

		Person[] pplArr = JsonPath.from(jsonStringArr).getObject("", Person[].class);
		System.out.println("JSONPATH ARRAY " + Arrays.toString(pplArr));

		List<Person> pplList = JsonPath.from(jsonStringArr).getList("", Person.class);
		System.out.println("JSONPATH LIST " + pplArr);

		ObjectMapper om = new ObjectMapper();
		Person[] arr = om.readValue(jsonStringArr, Person[].class);

		System.out.println("Array ---> " + Arrays.toString(arr));

		String jsonArray = om.writeValueAsString(arr);
		System.out.println("JSON Array ---> " + jsonArray);

		List<Person2> ppl = Arrays.asList(new Person2("aaa", 11), new Person2("bbb", 12), new Person2("ccc", 13));
		String jsonPPL = om.writeValueAsString(ppl);
		System.out.println("JSON List ---> " + jsonPPL);

		// converting to an Arraylist instead of Array
		// we need to use a typeReference object --> Type reference is a abstact class
		// with no abstract method thats why you see body{}
		List<Person> lst = om.readValue(jsonStringArr, new TypeReference<List<Person>>() {
		});

		System.out.println("List ---> " + lst);

	}

}

class Person2 {

	String name;
	int age;

	public Person2() {

	}

	public Person2(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person2 [name=" + name + ", age=" + age + "]";
	}

}

class Person {
	String name;
	int age;
	String driverId;
	String url;
	String givenName;
	String familyName;
	String dateOfBirth;
	String nationality;

	public Person() {

	}

	public Person(String name, int age, String driverId, String url, String givenName, String familyName,
			String dateOfBirth, String nationality) {
		super();
		this.name = name;
		this.age = age;
		this.driverId = driverId;
		this.url = url;
		this.givenName = givenName;
		this.familyName = familyName;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", driverId=" + driverId + ", url=" + url + ", givenName="
				+ givenName + ", familyName=" + familyName + ", dateOfBirth=" + dateOfBirth + ", nationality="
				+ nationality + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
