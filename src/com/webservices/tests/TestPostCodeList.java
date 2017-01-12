package com.webservices.tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.lessThan;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import au.com.bytecode.opencsv.CSVReader;

@RunWith(Parameterized.class)
public class TestPostCodeList {

	private String suburb;
	private String state;
	private String expectedPostCode;
    public static Response response;
	
	
	public TestPostCodeList(String suburb, String state, String expectedPostCode){
		this.suburb = suburb;
		this.state = state;	
		this.expectedPostCode = expectedPostCode;
	}
	
	@Parameters(name = "run:{index}, suburb:{0}, state:{1}, expected code:{2} ")
	public static List<String[]> data() throws IOException{
		String pathname = TestPostCodeList.class.getResource("/com/webservices/resources/PostCodeData.csv")
				.toString().replace("file:/", "").replace("%20",  " ");

		CSVReader reader = new CSVReader(new FileReader(pathname), ',', '\'', 1);
	    List<String[]> myEntries = reader.readAll();
	    reader.close();
	    return myEntries;
	}
	
	
	@Test
	 public void searchForPostCodeXMLTest()
	    {
			response =
			given().
	            header("AUTH-KEY", "5d5dc400-9405-4f17-9f82-251b69ebbd10").
	        when().
	            get("https://digitalapi.auspost.com.au/postcode/search.xml?q=" + suburb + "&state=" + state).
	        then().
	            contentType(ContentType.XML).
	            statusCode(200).
	            time(lessThan(4000L)).
	            body(hasXPath("/localities/locality/postcode[text()='" + expectedPostCode + "']")).
	            extract().response();
		        
		        System.out.println(response.getBody().asString());
	    }

}
