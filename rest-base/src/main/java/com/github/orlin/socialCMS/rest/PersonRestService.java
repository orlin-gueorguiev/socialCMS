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
import com.github.orlin.socialCMS.database.entities.PersonDao;
import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.PersonFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultPersonDBService;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.database.services.interfaces.PersonDBService;
import com.github.orlin.socialCMS.rest.AddressRestService.Address;
import com.github.orlin.socialCMS.rest.CompanyRestService.Company;
import com.github.orlin.socialCMS.rest.WebPresenceRestService.WebPresence;
import com.github.orlin.socialCMS.rest.jaxb.DateAdapters;
import com.github.orlin.socialCMS.rest.misc.Validator;

@Path("/person")
public class PersonRestService extends DefaultRestService<PersonDao, PersonFilter, PersonRestService.Person> {
	private PersonDBService service = new DefaultPersonDBService(getEntityManager());

	@Override
	public Person getXmlRepresentation(PersonDao object) {
		if(object == null) {
			return null;
		}
		
		Person person = new Person();
		
		person.created = object.getCreated();
		person.lastModified = object.getLastModified();
		person.id = object.getId();
		person.firstName = object.getFirstName();
		person.lastName = object.getLastName();
		person.address = AddressRestService.getXmlRepresentationStatic(object.getAddress());
		person.webPresence = WebPresenceRestService.getXmlRepresentationStatic(object.getWebPresence());
		person.company = CompanyRestService.getXmlRepresentationStatic(object.getCompany());
		
		return person;
	}

	@Override
	public PersonDao postEntity(MultivaluedMap<String, String> form) throws RestInputConstraintViolationException {
		String id = form.getFirst("id");
		
		PersonDao dao = null;
		if(StringUtils.isEmpty(id)) {
			dao = new PersonDao();
		} else {
			Long idL = Long.parseLong(id);
			dao = service.load(idL);
		}
		
		String firstName = form.getFirst("firstName");
		dao.setFirstName(firstName);
		
		String lastName = form.getFirst("lastName");
		dao.setLastName(lastName);
		
		loadWebPresence(form, dao);
		
		loadAddress(form, dao);
		
		loadCompany(form, dao);
		
		return dao;
	}

	private void loadCompany(MultivaluedMap<String, String> form, PersonDao dao) throws RestInputConstraintViolationException {
		String companyId = form.getFirst("companyId");
		Long cId = Validator.validateLongInput(companyId, "companyId", 1, -1, true);
		if(cId != null) {
			CompanyDao companyDao = new CompanyRestService().getDBService().load(cId);
			dao.setCompany(companyDao);
		}
	}

	private void loadAddress(MultivaluedMap<String, String> form, PersonDao dao) throws RestInputConstraintViolationException {
		String addressId = form.getFirst("addressId");
		Long addId = Validator.validateLongInput(addressId, "addressId", 1, -1, true);
		if(addId != null) {
			AddressRestService addressRestService = new AddressRestService();
			AddressDao addressDao = addressRestService.getDBService().loadAsReference(addId);
			dao.setAddress(addressDao);
		}
	}

	private void loadWebPresence(MultivaluedMap<String, String> form, PersonDao dao) throws RestInputConstraintViolationException {
		String webPresenceId = form.getFirst("webPresenceId");
		Long wpId = Validator.validateLongInput(webPresenceId, "webPresenceId", 1, -1, true);
		if(wpId != null) {
			WebPresenceRestService webPresenceWebService = new WebPresenceRestService();
			WebPresenceDao wp = webPresenceWebService.getDBService().loadAsReference(wpId);
			dao.setWebPresence(wp);
		}
	}

	@Override
	public String getEntityName() {
		return "Person";
	}
	
	@Override
	public PersonFilter getFilter() {
		return new PersonFilter();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends DBService<PersonDao, PersonFilter>> S getDBService() {
		return (S) service;
	}
	
	@XmlRootElement(name = "person")
	@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
	public static class Person extends JaxBObject {
		public Person() {
			super("person");
		}
		
		public Long id;
		public String firstName;
		public String lastName;
		public Address address;
		public WebPresence webPresence;
		public Company company;
		@XmlJavaTypeAdapter(DateAdapters.ShortDateAdapter.class)
		public Calendar created, lastModified;
		
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Person [id=");
			builder.append(id);
			builder.append(", firstName=");
			builder.append(firstName);
			builder.append(", lastName=");
			builder.append(lastName);
			builder.append(", address=");
			builder.append(address);
			builder.append(", webPresence=");
			builder.append(webPresence);
			builder.append(", company=");
			builder.append(company);
			builder.append(", created=");
			builder.append(created);
			builder.append(", lastModified=");
			builder.append(lastModified);
			builder.append("]");
			return builder.toString();
		}
	}
}
