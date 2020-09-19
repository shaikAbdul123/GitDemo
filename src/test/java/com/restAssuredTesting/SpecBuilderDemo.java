package com.restAssuredTesting;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoDemo.AddMapInput;
import pojoDemo.locationDetails;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderDemo {
	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		AddMapInput input = new AddMapInput();
		input.setAccuracy(50);
		input.setLanguage("French-IN");
		input.setName("Frontline house");
		input.setPhone_number("(+91) 983 893 3937");
		input.setAddress("29, side layout, cohen 09");
		input.setWebsite("http://google.com");
		//creating a list and then passing the list to types
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("saloon");
		input.setTypes(types);
		
		// creating locationDetails object and then passing it to main class
		locationDetails locationInput = new locationDetails();
		locationInput.setLat(-38.383494);
		locationInput.setLng(33.427362);
		
		input.setLocation(locationInput);
		
		// Creating request spec builder for code optimization
		
		 RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		 
		 // creating a response spec builder for code optimization
		 ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
		 .expectContentType(ContentType.JSON).build();
		
		 // Add place API
		RequestSpecification request= given().spec(requestSpec)
		.body(input);
		
		Response response = request.when().post("/maps/api/place/add/json")
		.then().spec(responseSpec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		JsonPath js = new JsonPath(responseString);
		String placeId = js.get("place_id");
		
		
		// get place API
		RequestSpecification getRequest = given().spec(requestSpec).queryParam("place_id", placeId);
		Response getResponse = getRequest.when().get("/maps/api/place/get/json")
		.then().spec(responseSpec).extract().response();
		String getPlaceResponse = getResponse.asString();
		System.out.println(getPlaceResponse);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
