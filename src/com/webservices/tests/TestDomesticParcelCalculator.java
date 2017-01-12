package com.webservices.tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class TestDomesticParcelCalculator {
	
    public static Response response;

	@Test
	 public void calculateDomesticParcelCostXML()
	    {
	        given().
	            header("AUTH-KEY", "5d5dc400-9405-4f17-9f82-251b69ebbd10").
	        when().
	            get("https://digitalapi.auspost.com.au/postage/parcel/domestic/calculate.xml?from_postcode=2079" + 
	        "&to_postcode=2151&length=20&width=20&weight=2&service_code=AUS_PARCEL_REGULAR&height=10").
	        then().
	            contentType(ContentType.XML).
	            statusCode(200).
	            time(lessThan(6000L)).
	            body(hasXPath("/postage_result/total_cost[text()='10.05']"));	        
	    }
	
	@Test
	 public void calculateDomesticParcelCostXML_definedParams()
	    {
	        response = 
			given().
	            header("AUTH-KEY", "5d5dc400-9405-4f17-9f82-251b69ebbd10").
	            param("from_postcode", "2079").
	            param("to_postcode", "2151").
	            param("length", "20").
	            param("width", "20").
	            param("weight", "2").
	            param("service_code", "AUS_PARCEL_REGULAR").
	            param("height", "10").
	        when().
	            get("https://digitalapi.auspost.com.au/postage/parcel/domestic/calculate.xml").
	        then().
	            contentType(ContentType.XML).
	            statusCode(200).
	            time(lessThan(6000L)).
	            body(hasXPath("/postage_result/total_cost[text()='10.05']")).
	            extract().response();
	        
	        System.out.println(response.getBody().asString());
	        String cost = response.xmlPath().getString("postage_result.total_cost");
	        System.out.println(cost);
	    }
}
