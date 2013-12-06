package com.github.orlin.socialCMS.database.services.impl.def;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.github.orlin.socialCMS.database.entities.CompanyDao;
import com.github.orlin.socialCMS.database.filters.CompanyFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CompanyDBService;

public class DefaultCompanyDBService extends
		GenericDBService<CompanyDao, CompanyFilter> implements
		CompanyDBService {

	public DefaultCompanyDBService(EntityManager em) {
		super(em);
	}

	@Override
	public Class<CompanyDao> getObjectClass() {
		return CompanyDao.class;
	}

	@Override
	public CompanyDao create() {
		return new CompanyDao();
	}

	@Override
	public CriteriaQuery<CompanyDao> generateCriteria(CompanyFilter filter,
			CriteriaQuery<CompanyDao> cq, CriteriaBuilder cb) {
		return cq;
	}

	@Override
	public CompanyFilter getNewFilterInstance() {
		return new CompanyFilter();
	}
}
