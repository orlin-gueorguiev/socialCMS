package com.github.orlin.socialCMS.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CompanyRestIT extends BaseRestIT {
	private String companyId;

	@Test
	public void crud() {
		_01createEntity();
		_05getEntity();
		_15deleteEntity();
	}
	
	public void _01createEntity() {
		WebResource webResource = generateWR("company");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("name", "test");

		ClientResponse response = webResource.accept(MediaType.TEXT_XML).post(ClientResponse.class, formData);

		Assert.assertEquals(200, response.getStatus());

		String responseStr = response.getEntity(String.class);
		companyId = getIdFromXml(responseStr);
	}
	
	public void _05getEntity() {
		WebResource webResource = generateWR("company/"+companyId);
		String response = webResource.accept(MediaType.TEXT_XML).get(String.class);
		Assert.assertTrue(response.contains("<id>" + companyId + "</id>"));
	}
	
	public void _15deleteEntity() {
		WebResource webResource = generateWR("company/"+companyId);
		ClientResponse response = webResource.accept(MediaType.TEXT_XML).delete(ClientResponse.class);
		Assert.assertEquals(204,response.getStatus());
	}
}
