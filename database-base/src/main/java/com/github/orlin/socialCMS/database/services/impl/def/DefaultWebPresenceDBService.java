package com.github.orlin.socialCMS.database.services.impl.def;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.WebPresenceFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;
import com.github.orlin.socialCMS.database.services.interfaces.WebPresenceDBService;

public class DefaultWebPresenceDBService extends GenericDBService<WebPresenceDao, WebPresenceFilter> implements
		WebPresenceDBService {

	public DefaultWebPresenceDBService(EntityManager em) {
		super(em);
	}

	@Override
	public WebPresenceDao create() {
		return new WebPresenceDao();
	}

	@Override
	public Class<WebPresenceDao> getObjectClass() {
		return WebPresenceDao.class;
	}

	@Override
	public CriteriaQuery<WebPresenceDao> generateCriteria(WebPresenceFilter filter, CriteriaQuery<WebPresenceDao> cq, CriteriaBuilder cb) {
		Root<WebPresenceDao> root = cq.from(getObjectClass());
		if(filter.getEmail() != null) {
			cq.where(cb.equal(root.get("email"), filter.getEmail()));
		 }

		 if(filter.getWebpage() != null) {
			 cq.where(cb.equal(root.get("webpage"), filter.getWebpage()));
		 }
		
		return cq;
	}

	@Override
	public WebPresenceFilter getNewFilterInstance() {
		return new WebPresenceFilter();
	}
}
