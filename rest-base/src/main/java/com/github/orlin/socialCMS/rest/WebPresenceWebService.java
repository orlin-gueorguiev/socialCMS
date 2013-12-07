package com.github.orlin.socialCMS.rest;

import java.util.Calendar;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.WebPresenceFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultWebPresenceDBService;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.database.services.interfaces.WebPresenceDBService;
import com.github.orlin.socialCMS.rest.jaxb.DateAdapters;
import com.github.orlin.socialCMS.rest.misc.Validator;

@Path("/webpresence")
public class WebPresenceWebService extends DefaultRestService<WebPresenceDao, WebPresenceFilter, WebPresenceWebService.WebPresence> {
	private static final String WEBPAGE_REGEXP = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;";
	public WebPresenceDBService service = new DefaultWebPresenceDBService(getEntityManager());
	
	
	@Override
	public WebPresenceFilter getFilter() {
		return new WebPresenceFilter();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends DBService<WebPresenceDao, WebPresenceFilter>> S getDBService() {
		return (S) service;
	}

	@Override
	public WebPresence getXmlRepresentation(WebPresenceDao object) {
		WebPresence wp = new WebPresence();
		wp.id = object.getId();
		wp.webpage = object.getWebpage();
		wp.email = object.getEmail();
		wp.created = object.getCreated();
		wp.lastModified = object.getLastModified();		
		return wp;
	}

	@Override
	public WebPresenceDao postEntity(MultivaluedMap<String, String> form) throws RestInputConstraintViolationException {
		String id = form.getFirst("id");
		
		WebPresenceDao dao = null;
		if(StringUtils.isEmpty(id)) {
			dao = getDBService().create();
		} else {
			Long idL = Long.parseLong(id);
			dao = getDBService().load(idL);
		}
		
		String webpage = form.getFirst("webpage");
		dao.setWebpage(Validator.validateStringInput(webpage, "webpage", true, WEBPAGE_REGEXP, 511));
		
		String email = form.getFirst("email");
		dao.setEmail(Validator.validateStringInput(email, "email", true, EMAIL_REGEXP, 255));
		
		return dao;
	}

	@Override
	public String getEntityName() {
		return "Web Presence";
	}
	
	@XmlRootElement(name = "address")
	@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
	public static class WebPresence extends JaxBObject {
		public WebPresence() {
			super("webPresence");
		}

		public Long id;
		public String email;
		public String webpage;
		@XmlJavaTypeAdapter(DateAdapters.ShortDateAdapter.class)
		public Calendar created, lastModified;
		
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("WebPresence [id=");
			builder.append(id);
			builder.append(", email=");
			builder.append(email);
			builder.append(", webpage=");
			builder.append(webpage);
			builder.append(", created=");
			builder.append(created);
			builder.append(", lastModified=");
			builder.append(lastModified);
			builder.append("]");
			return builder.toString();
		}
	}
}
