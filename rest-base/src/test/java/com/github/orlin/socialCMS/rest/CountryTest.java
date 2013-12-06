package com.github.orlin.socialCMS.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CountryTest {
	private String countryId;

	
//	@Test
	public void _01createCountry() throws Exception {
		Client client = Client.create();
		 
		WebResource webResource = client.resource("http://localhost:8080/socialCRMRest/country");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("name", "test");
		formData.add("zipCodePrefix", "test");
		formData.add("isoCountryCode", "test");
		
		ClientResponse response = webResource.accept(MediaType.TEXT_XML)
		   .post(ClientResponse.class, formData);
		
		Assert.assertEquals(200,response.getStatus());
		
		String responseStr = response.getEntity(String.class);
		Pattern idPattern = Pattern.compile(".*<id>(\\d+)</id>.*");
		
		Matcher matcher = idPattern.matcher(responseStr);
		Assert.assertTrue(matcher.matches());
		
		countryId = matcher.group(1);
	}
	
//	@Test
	public void _05getCountry() {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/socialCRMRest/country/"+countryId);
		String response = webResource.accept(MediaType.TEXT_XML).get(String.class);
		Assert.assertTrue(response.contains("<id>" + countryId + "</id>"));
	}
	
//	@Test
	public void _15deleteCountry() {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/socialCRMRest/country/"+countryId);
		ClientResponse response = webResource.accept(MediaType.TEXT_XML).delete(ClientResponse.class);
		Assert.assertEquals(204,response.getStatus());
	}
}
