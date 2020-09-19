package com.restAssuredTesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojoDemo.AddMapInput;
import pojoDemo.locationDetails;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {
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
		
		Response response = given().log().all().queryParam("key", "qaclick123")
		.body(input)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}

}
