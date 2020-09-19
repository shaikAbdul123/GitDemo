package com.restAssuredTesting;

import files.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonUSage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.mockJson());
		
		// print no of courses - applying size method on array
		int count  = js.getInt("courses.size()");
		System.out.println("No of courses present is " + count);
		
		// print purchase amount
		int purchaseAm = js.getInt("dashboard.purchaseAmount");
		System.out.println("purchase amount is "+ purchaseAm);
		
		// print title of first course
		String courseTitle = js.get("courses[0].title");
		System.out.println("Course title is " + courseTitle);
		
		// print all courses prices and their titles
		
		for(int i=0; i<count; i++)
		{
			String coursetitles = js.get("courses["+i+"].title");
			//int coursePrice = js.getInt("course["+i+"].price");
			//System.out.println(coursePrice);
			System.out.println(coursetitles);
			System.out.println(js.get("courses["+i+"].price").toString());
			
			
		}
		
		// print number of copies sold by RPA
		
		for(int i=0; i<count; i++)
		{
			String coursetitles = js.get("courses["+i+"].title");
			if(coursetitles.equalsIgnoreCase("RPA"))
			{
				//copies sold
				int copies = js.get("courses["+i+"].copies");
				System.out.println("No of copie sold are " + copies);
				break;
			}
		}
		
		// Verify if sum of all courses amount matches with purchase amount
		for(int i=0; i<count ; i++) {
			
			int  price = js.get("courses["+i+"].price");
			int noOfCopies = js.get("courses["+i+"].copies");
			int sum = price * noOfCopies ;
			System.out.println(sum);
		}
		
		
	}

}
