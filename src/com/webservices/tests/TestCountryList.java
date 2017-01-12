package com.webservices.tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.lessThan;

import java.util.List;

import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class TestCountryList {

    public static Response response;
    public static String jsonAsString;
    
	@Test
	 public void countryListReturnsAustriaXML()
	    {
	        given().
	            header("AUTH-KEY", "5d5dc400-9405-4f17-9f82-251b69ebbd10").
	        when().
	           get("https://auspost.com.au/api/postage/country.xml").
	        then().
	            contentType(ContentType.XML).
	            statusCode(200).
	            body(containsString("Algeria")).
	            time(lessThan(3000L));	 
	    }

	@Test
	 public void countryListReturnsAustriaJSON()
	    {
		String nameCheck = "SOUTH AFRICA";
		String codeCheck = null;
		
	       response =
	    	given().
	            header("AUTH-KEY", "5d5dc400-9405-4f17-9f82-251b69ebbd10").
	        when().
	            get("https://auspost.com.au/api/postage/country.json").
	        then().
	            contentType(ContentType.JSON).
	            statusCode(200).
	            body(containsString("Austria")).
	            time(lessThan(3000L)).
	            extract().response();
   
	        jsonAsString = response.asString();
	        System.out.println(jsonAsString);	
	        
	        List<String> countryNames = response.jsonPath().getList("countries.country.name");
	        int count = 0;
	        for (String names : countryNames) {
		        System.out.println(names);
		         
		        if (names.equals(nameCheck)) {
		        	codeCheck = response.jsonPath().getList("countries.country.code").get(count).toString();
		        }
		    count = count + 1;
	        }
	        System.out.println("Country found = "+nameCheck+" and country code is "+codeCheck);
	    }
}
