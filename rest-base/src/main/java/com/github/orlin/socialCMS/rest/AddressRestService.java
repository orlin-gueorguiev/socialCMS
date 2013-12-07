package com.github.orlin.socialCMS.rest;

import java.util.Calendar;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultAddressDBService;
import com.github.orlin.socialCMS.database.services.interfaces.AddressDBService;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.rest.jaxb.DateAdapters;
import com.github.orlin.socialCMS.rest.misc.Validator;

@Path("/address")
public class AddressRestService extends DefaultRestService<AddressDao, AddressFilter, AddressRestService.Address> {
	private AddressDBService service = new DefaultAddressDBService(getEntityManager());

	@Override
	public AddressFilter getFilter() {
		return new AddressFilter();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends DBService<AddressDao, AddressFilter>> S getDBService() {
		return (S) service;
	}

	@Override
	public Address getXmlRepresentation(AddressDao object) {
		return AddressRestService.getXmlRepresentationStatic(object);
	}
	
	public static Address getXmlRepresentationStatic(AddressDao object) {
		if(object == null) {
			return null;
		}
		
		
		Address address = new Address();
		address.id = object.getId();
		address.country = CountryRestService.getXmlRepresentationStatic(object.getCountry());
		address.zipCode = object.getZipCode();
		address.street = object.getStreet();
		address.created = object.getCreated();
		address.lastModified = object.getLastModified();				
		return address;
	}

	@Override
	public AddressDao postEntity(MultivaluedMap<String, String> form) throws RestInputConstraintViolationException {
		CountryDao countryDao = getCountryDao(form);
		
		String id = form.getFirst("id");
		
		AddressDao dao = null;
		if(StringUtils.isEmpty(id)) {
			dao = new AddressDao();
		} else {
			Long idL = Long.parseLong(id);
			dao = getDBService().load(idL);
		}
		
		dao.setCountry(countryDao);
		
		String zipCode = form.getFirst("zipCode");
		Integer zip = Validator.validateIntegerInput(zipCode, "zipCode", 1000, 99999, false);
		dao.setZipCode(zip);
		
		String street = form.getFirst("street");
		dao.setStreet(Validator.validateStringInput(street, "street", false, 16, 255));	
		
		return dao;
	}

	private CountryDao getCountryDao(MultivaluedMap<String, String> form) throws RestInputConstraintViolationException {
		String country = form.getFirst("countryId");
		if(!StringUtils.isNumeric(country)) {
			throw RestInputConstraintViolationException.formInputMissing("countryId");
		}
		Long countryId = Long.parseLong(country);
		
		CountryRestService crs = new CountryRestService();
		CountryDao countryDao = crs.getDBService().load(countryId);
		
		if(countryDao == null) {
			throw RestInputConstraintViolationException.entityNotFound("country", country);
		}
		return countryDao;
	}

	@Override
	public String getEntityName() {
		return "Address";
	}
	
	
	@XmlRootElement(name = "address")
	@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
	public static class Address extends JaxBObject {
		public Address() {
			super("address");
		}

		public Long id;
		public CountryRestService.Country country;
		public Integer zipCode;
		public String street;
		@XmlJavaTypeAdapter(DateAdapters.ShortDateAdapter.class)
		public Calendar created, lastModified;
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Address [id=");
			builder.append(id);
			builder.append(", country=");
			builder.append(country);
			builder.append(", zipCode=");
			builder.append(zipCode);
			builder.append(", street=");
			builder.append(street);
			builder.append(", created=");
			builder.append(created);
			builder.append(", lastModified=");
			builder.append(lastModified);
			builder.append("]");
			return builder.toString();
		}
	}
}
