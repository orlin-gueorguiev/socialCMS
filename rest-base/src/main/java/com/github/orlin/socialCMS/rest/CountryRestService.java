package com.github.orlin.socialCMS.rest;

import java.util.Calendar;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.CountryFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultCountryDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CountryDBService;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.rest.jaxb.DateAdapters;

@Path("/country")
public class CountryRestService extends DefaultRestService<CountryDao, CountryFilter, CountryRestService.Country> {

	private CountryDBService service = new DefaultCountryDBService(getEntityManager());
		
	@Override
	public String getEntityName() {
		return "Country";
	}


	@SuppressWarnings("unchecked")
	@Override
	public <S extends DBService<CountryDao, CountryFilter>> S getDBService() {
		return (S) service;
	}


	@Override
	public CountryFilter getFilter() {
		return new CountryFilter();
	}

	@Override
	public Country getXmlRepresentation(CountryDao object) {
		return CountryRestService.getXmlRepresentationStatic(object);
	}
	
	public static Country getXmlRepresentationStatic(CountryDao object) {
		Country country = new Country();
		country.id = object.getId();
		country.name = object.getNameInDefaultLanguage();
		country.created = object.getCreated();
		country.lastModified = object.getLastModified();
		country.zipCodePrefix = object.getZipCodePrefix();
		country.isoCountryCode = object.getIsoCountryCode();
		
		return country;
	}
	
	
	
	@XmlRootElement(name = "country")
	@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
	public static class Country extends JaxBObject {
		public Country() {
			super("country");
		}

		public Long id;
		public String name;
		public String zipCodePrefix;
		public String isoCountryCode;
		@XmlJavaTypeAdapter(DateAdapters.ShortDateAdapter.class)
		public Calendar created, lastModified;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Country [name=");
			builder.append(name);
			builder.append(", zipCodePrefix=");
			builder.append(zipCodePrefix);
			builder.append(", isoCountryCode=");
			builder.append(isoCountryCode);
			builder.append(", created=");
			builder.append(created);
			builder.append(", lastModified=");
			builder.append(lastModified);
			builder.append("]");
			return builder.toString();
		}
	}

	@Override
	public CountryDao postEntity(MultivaluedMap<String, String> form) {		
		String id = form.getFirst("id");
		
		CountryDao countryDao = null;
		if(StringUtils.isEmpty(id)) {
			countryDao = new CountryDao();
		} else {
			Long idL = Long.parseLong(id);
			countryDao = getDBService().load(idL);
		}
		
		String name = form.getFirst("name");
		if(StringUtils.isNotEmpty(name)) {
			countryDao.setNameInDefaultLanguage(name);
		}
		
		String zipCode = form.getFirst("zipCodePrefix");
		if(StringUtils.isNotEmpty(zipCode)) {
			countryDao.setZipCodePrefix(zipCode);
		}
		
		String iso = form.getFirst("isoCountryCode");
		if(StringUtils.isNotEmpty(iso)) {
			countryDao.setIsoCountryCode(iso);
		}

		return countryDao;
	}
}
