package com.restAssuredTesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ResuableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJsonPayload {
	
	
	@Test(dataProvider = "BooksData")
	public void AddBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String AddBookResponse = given()
		.header("Content-Type", "application/json")
		.body(payload.AddBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ResuableMethods.rawToJson(AddBookResponse);
		String id = js.getString("ID");
		System.out.println("the id of the book added is "+ id);
		
	}
	
	@Test(dataProvider = "BooksData")
	public void deleteBook(String isbn, String aisle) {
	
		RestAssured.baseURI = "http://216.10.245.166";
		given()
		.header("Content-Type", "application/json")
		.body(payload.DeleteBook(isbn, aisle))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		//Array collection of elements
		//multi dimensional array - collection of arrays
		// new object[][] {array1{isbn,aisle},array2,array3}; - creating an object and initializing it
		return new Object[][] {{"POM3","4121"}};
		//return new Object[][] {{"DOM","6541"},{"POM","3256"},{"KOM","6931"}};
	}
	
	
	

}
