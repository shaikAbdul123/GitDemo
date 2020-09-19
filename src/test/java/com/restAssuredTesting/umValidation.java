package com.restAssuredTesting;

import javax.xml.crypto.KeySelector.Purpose;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class umValidation {
	
	@Test
	public void sumOfCourses() {
		
		JsonPath js = new JsonPath(payload.mockJson());
		int count = js.getInt("courses.size()");
		int sum = 0;
		for(int i=0; i<count ; i++) {
			int  price = js.get("courses["+i+"].price");
			int noOfCopies = js.get("courses["+i+"].copies");
			int amount = price * noOfCopies ;
			sum = sum + amount;
			
		}
		System.out.println(sum);
		int puchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(puchaseAmount);
		Assert.assertEquals(sum, puchaseAmount);
	}
}
