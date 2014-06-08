package com.github.orlin.socialCMS.database.services.impl.def;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.github.orlin.socialCMS.database.entities.PersonDao;
import com.github.orlin.socialCMS.database.filters.PersonFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;
import com.github.orlin.socialCMS.database.services.interfaces.PersonDBService;

public class DefaultPersonDBService extends GenericDBService<PersonDao, PersonFilter> implements PersonDBService {
	public DefaultPersonDBService(EntityManager em) {
		super(em);
	}
	
	private static final Logger log = Logger.getLogger(DefaultPersonDBService.class.toString());

	@Override
	public PersonDao create() {
		return new PersonDao();
	}
	
	@Override
	public Class<PersonDao> getObjectClass() {
		return PersonDao.class;
	}

	@Override
	public PersonFilter getNewFilterInstance() {
		return new PersonFilter();
	}
	
	@SuppressWarnings("unused")
	@Override
	public CriteriaQuery<PersonDao> generateCriteria(PersonFilter filter, CriteriaQuery<PersonDao> cq, CriteriaBuilder cb) {
		Root<PersonDao> person = cq.from(getObjectClass());

		log.log(Level.FINEST, "Person currently has no implemented person specific filtering");
		// TODO currently no filtering (beside the defualt one) for person
		return cq;
	}
}
