/**
 * 
 */
package com.automation.base;

import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseAPITest extends BaseFixture {

	private static String accessToken;
	private static String userID;

	public BaseAPITest() {
		try {
			System.out.println(getProperties().getProperty("appUsername"));
			loginAndGetToken(getProperties().getProperty("appUsername"), getProperties().getProperty("appPassword"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getUserID() {
		return userID;
	}

	/**
	 * A static utility method to get the token by providing username and password
	 * as Post request to /Login endpoint and capture the token field from response
	 * json
	 * 
	 * @param username
	 * @param password
	 * @return the token as String from the response json
	 */
	public void loginAndGetToken(String username, String password) {
		RestAssured.baseURI = getProperties().getProperty("OKTAAPIURL");

		System.out.println("Before Rest Call");
		Response response = RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body("{\r\n \"username\": \"" + username + "\",\r\n \"password\": \"" + password + "\"\r\n}")
				.post("/login");
		System.out.println("After Rest Call");
		System.out.println(response.statusCode());

		accessToken = response.jsonPath().getString("accessToken");
		userID = response.jsonPath().getString("user.id");
		System.out.println(accessToken);
		System.out.println("userID --> " + userID);

	}

}
