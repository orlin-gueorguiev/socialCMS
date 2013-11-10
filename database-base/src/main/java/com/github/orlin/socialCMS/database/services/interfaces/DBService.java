package com.github.orlin.socialCMS.database.services.interfaces;

import java.util.List;

import com.github.orlin.socialCMS.database.general.Filter;

/**
 * The DB service layer allows us to use the db without the need to use the underlying layer
 * directly.
 * 
 * @author orlin
 *
 * @param <T> The type of object, which the DB Service supports
 * @param <F> the filter type, which is supported
 */
public interface DBService<T, F extends Filter> {
	/**
	 * Loads the db object by it's id.
	 * @param id The DB Id of the object
	 * @return If no such object in the DB, return <b>null</b>.
	 */
	public T load(Long id);
	
	/**
	 * Saves (or updates) the object to the DB
	 * @param savable The object to save
	 */
	public T save(T savable);
	
	/**
	 * Deletes the object from the DB. If the object was not persisted, does nothing.
	 * @param saved The saved object, which needs to be deleted
	 */
	public void delete(T saved);
	
	/**
	 * Queries the DB and returns all the instances, which comply with the given criterias
	 * @param filter the criteria
	 * @return the collection
	 */
	public List<T> loadAllByFilter(F filter);
	
	/**
	 * 
	 * @param filter
	 * @return 
	 * <ul>
	 * 	<li><i>When 0 instances:</i> <b>null</b></li>
	 * 	<li><i>When 1 instance:</i> <b>the instance</b></li>
	 * 	<li><i>When more then 1 instance:</i> <b>TooManyAnswersRuntimeException</b></li>
	 * </ul>
	 * 
	 */
	public T loadByFilter(F filter);
	
	/**
	 * Creates a new persistable Object
	 * @return
	 */
	public T create();
	
	
	/**
	 * Loads all the instances of the data from the Database 
	 * @return
	 */
	public List<T> loadAll();
	
	/**
	 * @return Returns the size of all elements (of this type), which are in the db
	 */
	public Long getSize(); 
	
	
	/**
	 * @param filter
	 * @return Returns the size of all elements (of this type) by filter
	 */
	public Long getSizeByFilter(F filter);
}