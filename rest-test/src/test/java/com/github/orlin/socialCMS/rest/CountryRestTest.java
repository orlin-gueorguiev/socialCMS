package com.github.orlin.socialCMS.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CountryRestTest extends BaseRestTest {
	private String countryId;

	
	@Test
	public void _01createCountry() throws Exception {
		WebResource webResource = generateWR("country");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("name", "test");
		formData.add("zipCodePrefix", "test");
		formData.add("isoCountryCode", "test");
		
		ClientResponse response = webResource.accept(MediaType.TEXT_XML)
		   .post(ClientResponse.class, formData);
		
		Assert.assertEquals(200,response.getStatus());
		
		String responseStr = response.getEntity(String.class);
		countryId = getIdFromXml(responseStr);
	}
	
	@Test
	public void _05getCountry() {
		WebResource webResource = generateWR("country/"+countryId);
		String response = webResource.accept(MediaType.TEXT_XML).get(String.class);
		Assert.assertTrue(response.contains("<id>" + countryId + "</id>"));
	}
	
	@Test
	public void _15deleteCountry() {
		WebResource webResource = generateWR("country/"+countryId);
		ClientResponse response = webResource.accept(MediaType.TEXT_XML).delete(ClientResponse.class);
		Assert.assertEquals(204,response.getStatus());
	}
}
