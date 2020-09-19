package com.restAssuredTesting;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraAddCommentToIssue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8080";

		// login scenario
		SessionFilter session = new SessionFilter();
		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body("{ \"username\": \"subhan\", \"password\": \"subhan915\" }").log().all().filter(session).when()
				.post("/rest/auth/1/session").then().log().all().extract().response().asString();

		// Add comment
		String expectedMessage = "Shaik smriti saga rakesh";
		String addCommentResponse = given().pathParam("id", "10004").log().all()
				.header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"" + expectedMessage + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");

		// Adding attachment
		given().pathParam("id", "10004").header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").filter(session)
				.multiPart("file",
						new File("C:\\Users\\admin\\Desktop\\API Automation\\restAssuredTesting\\bugAttachement.txt"))
				.when().post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

		// Get issue details
		String issueDetails = given().filter(session).pathParam("id", "10004").queryParam("fields", "comment").log()
				.all().when().get("/rest/api/2/issue/{id}").then().log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount = js1.get("fields.comment.comments.size()");
		for (int i = 0; i < commentsCount; i++) {
			String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId)) {
				String message = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}

		}

	}

}
