package com.github.orlin.socialCMS.rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.github.orlin.socialCMS.database.general.Filter;
import com.github.orlin.socialCMS.database.services.interfaces.DBService;
import com.github.orlin.socialCMS.rest.jaxb.StandartRestReturnObject;

/**
 * The generic Rest class, which should ease the default creation of a rest
 * service.
 * 
 * <h2>How to use:</h2>
 * <ol>
 * <li>Extend the new REST service with this class</li>
 * <li>T: the DAO, such as CountryDao</li>
 * <li>F: the Filter, such as CountryFilter</li>
 * <li>J: the JaxBObject, which is a representation of the DAO object</li>
 * <li>Set on class level: @javax.ws.rs.Path{"/entity"}, where entity is the
 * entity, such as country</li>
 * <li>Add the JaxBObject to StandartRestReturnObject &gt; XmlSeeAlso</li>
 * <li>Implement postEntity(...)</li>
 * <li></li>
 * 
 * </ol>
 * 
 * 
 * <h2>What does this offer:</h2>
 * 
 * <ol>
 * <li>Get by id &ltentity/id&gt, such as <i>country/15</i>, which returns the
 * country with id 15, or 404, if it does not exist</li>
 * <li>Delete by id &ltentity/id&gt, such as <i>country/15</i>, which deletes
 * the country with id 15, or 404, if it does not exist</li>
 * <li>Get all, optional querry params: <i>pagination</i> and <i>startFrom</i>,
 * for example <i>country?pagination=50</i></li>
 * 
 * 
 * </ol>
 * 
 * @author orlin
 * 
 */

// TODO write documentation
public abstract class DefaultRestService<T, F extends Filter, J extends JaxBObject> {

	/**
	 * @return Returns a new instance of the filter
	 */
	public abstract F getFilter();
	
	/**
	 * @return Returns a new entity of the specific DTO
	 */
	public abstract T getEntity();

	/**
	 * @return Returns the DB Service for the specific Rest Service
	 */
	public abstract <S extends DBService<T, F>> S getDBService();

	/**
	 * Gets a XML representation of the object (JaxB object)
	 * @param object the DTO object
	 * @return the JaxB object
	 */
	public abstract J getXmlRepresentation(T object);
	
	/**
	 * Create or update entity from post
	 * @param form the form params
	 * @return the DTO
	 */
	public abstract T postEntity(MultivaluedMap<String, String> form);
	
	public DBService<T, F> service;

	{
		service = getDBService();
	}

	/**
	 * @return Returns the human readable name of the entity, for example
	 *         WebPresenceDao to Web Presence
	 */
	public abstract String getEntityName();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{entityId}")
	public String getById(@PathParam("entityId") Long entityId) {
		T entity = service.load(entityId);

		if (entity == null) {
			throw new WebApplicationException(404);
		}

		return entity.toString();
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{entityId}")
	public String deleteById(@PathParam("entityId") Long entityId) {
		T entity = service.load(entityId);

		if (entity == null) {
			throw new WebApplicationException(404);
		}

		service.delete(entity);

		throw new WebApplicationException(204);
	}

	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public StandartRestReturnObject<J> getAll(@QueryParam("pagination") @DefaultValue("10") Integer pagination, @QueryParam("startFrom") @DefaultValue("0") Integer startFrom) {

		F filter = getFilter();
		filter.setCount(pagination);
		filter.setFirstResult(startFrom);
		
		StandartRestReturnObject<J> restQueryAnswer = new StandartRestReturnObject<>();
		restQueryAnswer.setTotalObjects(service.getSizeByFilter(filter));
		List<T> loadAllByFilter = service.loadAllByFilter(filter);
		List<J> converted = new LinkedList<>();
		for (T loaded: loadAllByFilter) {
			J representation = getXmlRepresentation(loaded);
			converted.add(representation);
		}
		restQueryAnswer.setQueriedObject(converted);

		return restQueryAnswer;
	}
	
	@POST
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> form) {
		T createdOrUpdatedEntity = postEntity(form);
		
		J entity = getXmlRepresentation(createdOrUpdatedEntity);

		Response response = Response.ok(entity).build();
		
		return response;
	}

	@PUT
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	@Consumes("application/x-www-form-urlencoded")
	public Response put(MultivaluedMap<String, String> form) {
		return post(form);
	}

}
