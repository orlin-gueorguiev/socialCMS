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
import com.github.orlin.socialCMS.database.entities.CompanyDao;
import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.CompanyFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultCompanyDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CompanyDBService;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.rest.AddressRestService.Address;
import com.github.orlin.socialCMS.rest.WebPresenceRestService.WebPresence;
import com.github.orlin.socialCMS.rest.jaxb.DateAdapters;
import com.github.orlin.socialCMS.rest.misc.Validator;

@Path("/company")
public class CompanyRestService extends DefaultRestService<CompanyDao, CompanyFilter, CompanyRestService.Company> {
	private CompanyDBService service = new DefaultCompanyDBService(getEntityManager());

	@SuppressWarnings("unchecked")
	@Override
	public <S extends DBService<CompanyDao, CompanyFilter>> S getDBService() {
		return (S) service;
	}

	public static Company getXmlRepresentationStatic(CompanyDao object) {
		if(object == null) {
			return null;
		}
		
		Company company = new Company();
		
		company.id = object.getId();
		company.name = object.getName();
		company.address = AddressRestService.getXmlRepresentationStatic(object.getAddress());
		company.webPresence = WebPresenceRestService.getXmlRepresentationStatic(object.getWebPresence());
				
		company.created = object.getCreated();
		company.lastModified = object.getLastModified();
		
		return company;
	}
	
	@Override
	public Company getXmlRepresentation(CompanyDao object) {
		return getXmlRepresentationStatic(object);
	}

	@Override
	public CompanyDao postEntity(MultivaluedMap<String, String> form) throws RestInputConstraintViolationException {
		String id = form.getFirst("id");
		
		CompanyDao dao = null;
		if(StringUtils.isEmpty(id)) {
			dao = new CompanyDao();
		} else {
			Long idL = Long.parseLong(id);
			dao = service.load(idL);
		}
				
		loadAddressId(form, dao);
		
		loadWebPresence(form, dao);		
		
		String name = form.getFirst("name");
		dao.setName(Validator.validateStringInput(name, "name", false, 3, 255));
		
		return dao;
	}

	private void loadAddressId(MultivaluedMap<String, String> form, CompanyDao dao) throws RestInputConstraintViolationException {
		String addressId = form.getFirst("addressId");
		Long addId = Validator.validateLongInput(addressId, "addressId", 1, -1, true);
		if(addId != null) {
			AddressDao addressDao = new AddressRestService().getDBService().load(addId);
			dao.setAddress(addressDao);
		}
	}

	private void loadWebPresence(MultivaluedMap<String, String> form, CompanyDao dao) throws RestInputConstraintViolationException {
		String webPresenceId = form.getFirst("webPresenceId");
		Long wpId = Validator.validateLongInput(webPresenceId, "webPresenceId", 1, -1, true);
		if(wpId != null) {
			WebPresenceDao loaded = new WebPresenceRestService().getDBService().load(wpId);
			dao.setWebPresence(loaded);
		}
	}
	
	@Override
	public CompanyFilter getFilter() {
		return new CompanyFilter();
	}
	
	@Override
	public String getEntityName() {
		return "Company";
	}
	
	@XmlRootElement(name = "company")
	@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
	public static class Company extends JaxBObject {
		public Company() {
			super("company");
		}
		
		public String name;
		public Long id;
		public Address address;
		public WebPresence webPresence;
		@XmlJavaTypeAdapter(DateAdapters.ShortDateAdapter.class)
		public Calendar created, lastModified;
		
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Company [name=");
			builder.append(name);
			builder.append(", id=");
			builder.append(id);
			builder.append(", address=");
			builder.append(address);
			builder.append(", webPresence=");
			builder.append(webPresence);
			builder.append(", created=");
			builder.append(created);
			builder.append(", lastModified=");
			builder.append(lastModified);
			builder.append("]");
			return builder.toString();
		}
		
	}
}
