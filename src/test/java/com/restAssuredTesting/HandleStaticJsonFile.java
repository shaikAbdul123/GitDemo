package com.restAssuredTesting;

import org.testng.annotations.Test;

import files.ResuableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HandleStaticJsonFile {
	
	@Test
	public void AddBook() throws IOException {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String resp =  given()
		.header("Content-Type", "application/json")
		.body(GenerateStringFromResource("C:\\Users\\admin\\Desktop\\AddBookdetails.json"))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ResuableMethods.rawToJson(resp);
		String id = js.get("ID");
		System.out.println("the id of the book added is "+ id);
	}
	
	public static String  GenerateStringFromResource(String path) throws IOException {
		
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	

}
