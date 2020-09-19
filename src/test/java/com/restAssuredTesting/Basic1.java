package com.restAssuredTesting;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ResuableMethods;
import files.payload;

public class Basic1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//Add place 
		String response = given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body(payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println("the reponse is" + response);
		//JsonPath js = new JsonPath(response); //for parsing json, takes string as input
		JsonPath js = ResuableMethods.rawToJson(response);
		String placeId = js.getString("place_id"); //path is the location of the attributed to be extracted from JSON
		System.out.println("placeid is " + placeId);
		
		// update place
		String newAddress = "13-6-437/A/85/5 2nd floor";
		
		given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"name\": \"khader bagh\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//get place details
		String response1 = given().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		//JsonPath js1 = new JsonPath(response1);
		JsonPath js2 = ResuableMethods.rawToJson(response1);
		String actualAddress = js2.getString("address");
		System.out.println("the address provided is " + actualAddress);
		
		// junit or TestNG
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}

}
