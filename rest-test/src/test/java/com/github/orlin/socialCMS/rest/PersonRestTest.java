package com.github.orlin.socialCMS.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PersonRestTest extends BaseRestTest {
	private String entityId;

	
	@Test
	public void _01createEntity() throws Exception {
		WebResource webResource = generateWR("person");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("firstName", "test");
		formData.add("lastName", "test");
		
		ClientResponse response = webResource.accept(MediaType.TEXT_XML)
		   .post(ClientResponse.class, formData);
		
		Assert.assertEquals(200,response.getStatus());
		
		String responseStr = response.getEntity(String.class);
		entityId = getIdFromXml(responseStr);
	}
	
	@Test
	public void _05getEntity() {
		WebResource webResource = generateWR("person/"+entityId);
		String response = webResource.accept(MediaType.TEXT_XML).get(String.class);
		Assert.assertTrue(response.contains("<id>" + entityId + "</id>"));
	}
	
	@Test
	public void _15deleteEntity() {
		WebResource webResource = generateWR("person/"+entityId);
		ClientResponse response = webResource.accept(MediaType.TEXT_XML).delete(ClientResponse.class);
		Assert.assertEquals(204,response.getStatus());
	}
}
