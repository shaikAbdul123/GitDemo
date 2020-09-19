package com.restAssuredTesting;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojoDemo.Api;
import pojoDemo.GetCourseResponse;
import pojoDemo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class OauthDemo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//google has now stopped the automation wrt login
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("subhantheleader@gmail.com");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("XXXX");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(4000);
//		String url = driver.getCurrentUrl();
		
		String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F3wEOYmqr9AcnGbuDkGcs-nt-6laiU6LTr6Fs9m05Cn8ZK_rhqtTxdqmuvHTRLdBLOWJoDUzZyJzICMskdRwsIGs&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);
		
		String accessTokenResponse =  given().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code").log().all()
		.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");	
		System.out.println(accessToken);
		
		//get courses from rahulshetty academy
//		String response = given().queryParam("access_token", accessToken)
//		.when().log().all().
//		get("https://rahulshettyacademy.com/getCourse.php").asString();
//		System.out.println(response);
		
		//pojo demo - deserializing json response to pojo classes
		GetCourseResponse gc = given().queryParam("access_token", accessToken)
	    .expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourseResponse.class);
		
		System.out.println("the url for linked in is" + gc.getLinkedIn());
		System.out.println("the name of the instructor is "+ gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//get price of APi course
		List<Api> apiCoursesCount = gc.getCourses().getApi();
		for(int i=0; i<apiCoursesCount.size(); i++) {
			
			if(apiCoursesCount.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java"))
			{
				System.out.println("Price of the course is " + apiCoursesCount.get(i).getPrice());
				break;
			}
		}
		
		//get course title names of web automation and comparing two arrays
		ArrayList<String> actualTitle = new ArrayList<String>();
		List<WebAutomation> webCoursesCount = gc.getCourses().getWebAutomation();
		for(int i=0; i<webCoursesCount.size(); i++)
		{
			actualTitle.add(webCoursesCount.get(i).getCourseTitle());
		}
		
		//converting above input array to array list for comparison
		List<String> expectedList =   Arrays.asList(courseTitles);
		Assert.assertTrue(actualTitle.equals(expectedList));
	}

}
