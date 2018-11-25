package restAPITesting;

import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomSerializeDeserialize {

	@Test
	public void test() throws Exception {

		String greeting = "{\"name\":\"Adam\", \"message\":\"Hello\"}";

		String greeting2 = "{\"name\":\"Adam\", \"message\":\"Hello\", \"extraMessage\":\"goodbye\"}";

		ObjectMapper mapper = new ObjectMapper();
		Greeting g1 = mapper.readValue(greeting2, Greeting.class);

		System.out.println(g1);

		System.out.println("=====");
		String str = mapper.writeValueAsString(g1);
		System.out.println(str);
		
		Greeting g2 = new Greeting("Akbar","GoodNight") ; 
	    System.out.println(  mapper.writeValueAsString(g2) );

	}

	@Test
	public void createPojoGoTCharaccter() throws Exception {

		/// Create GOT_Character ---> id , male , house , name

		String peopleJson = " [\n" + 
			      "    {\n" + 
			      "        \"_id\": \"56ffc5be043244081938576d\",\n" + 
			      "        \"male\": true,\n" + 
			      "        \"house\": \"House Hightower\",\n" + 
			      "        \"slug\": \"Abelar_Hightower\",\n" + 
			      "        \"name\": \"Abelar Hightower\",\n" + 
			      "        \"__v\": 0,\n" + 
			      "        \"pageRank\": 2.5,\n" + 
			      "        \"books\": [\n" + 
			      "            \"The Hedge Knight\"\n" + 
			      "        ],\n" + 
			      "        \"updatedAt\": \"2016-04-02T13:14:38.834Z\",\n" + 
			      "        \"createdAt\": \"2016-04-02T13:14:38.834Z\",\n" + 
			      "        \"titles\": [\n" + 
			      "            \"Ser\"\n" + 
			      "        ]\n" + 
			      "    },\n" + 
			      "    {\n" + 
			      "        \"_id\": \"56ffc5be043244081938576e\",\n" + 
			      "        \"male\": true,\n" + 
			      "        \"house\": \"House Frey\",\n" + 
			      "        \"slug\": \"Addam_Frey\",\n" + 
			      "        \"name\": \"Addam Frey\",\n" + 
			      "        \"__v\": 0,\n" + 
			      "        \"pageRank\": 4.5,\n" + 
			      "        \"books\": [\n" + 
			      "            \"The Mystery Knight\"\n" + 
			      "        ],\n" + 
			      "        \"updatedAt\": \"2016-04-02T13:14:38.875Z\",\n" + 
			      "        \"createdAt\": \"2016-04-02T13:14:38.875Z\",\n" + 
			      "        \"titles\": [\n" + 
			      "            \"Ser\"\n" + 
			      "        ]\n" + 
			      "    },\n" + 
			      "    {\n" + 
			      "        \"_id\": \"56ffc5be043244081938576f\",\n" + 
			      "        \"male\": true,\n" + 
			      "        \"slug\": \"Addam\",\n" + 
			      "        \"name\": \"Addam\",\n" + 
			      "        \"__v\": 0,\n" + 
			      "        \"pageRank\": 1.5,\n" + 
			      "        \"books\": [\n" + 
			      "            \"The Mystery Knight\"\n" + 
			      "        ],\n" + 
			      "        \"updatedAt\": \"2016-04-02T13:14:38.877Z\",\n" + 
			      "        \"createdAt\": \"2016-04-02T13:14:38.877Z\",\n" + 
			      "        \"titles\": [\n" + 
			      "            \"Ser\"\n" + 
			      "        ]\n" + 
			      "    } ] " ;

	
		ObjectMapper mapper = new ObjectMapper();
		GotCharacter[] g1 = mapper.readValue(peopleJson, GotCharacter[].class);

		System.out.println(Arrays.toString(g1));

		System.out.println("=====");
		String strGot = mapper.writeValueAsString(peopleJson);
		System.out.println(strGot);
	
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class GotCharacter {
	String id;
	Boolean male;
	String house;
	String name;

	public GotCharacter() {
	}

	public GotCharacter(String id, Boolean male, String house, String name) {
		super();
		this.id = id;
		this.male = male;
		this.house = house;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GotCharacter [id=" + id + ", male=" + male + ", house=" + house + ", name=" + name + "]";
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Greeting {
	String name;
	String message;

	public Greeting() {

	}

	public Greeting(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Greeting [name=" + name + ", message=" + message + "]";
	}

}