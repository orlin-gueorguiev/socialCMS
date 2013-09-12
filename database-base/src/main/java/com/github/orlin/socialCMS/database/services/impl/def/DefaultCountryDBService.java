package com.github.orlin.socialCMS.database.services.impl.def;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.CountryFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CountryDBService;

public class DefaultCountryDBService extends GenericDBService<CountryDao, CountryFilter> implements CountryDBService<CountryDao, CountryFilter> {

	@Override
	public Class<CountryDao> getObjectClass() {
		return CountryDao.class;
	}

	@Override
	public CountryDao create() {
		return new CountryDao();
	}

	@Override
	public CriteriaQuery<CountryDao> generateCriteria(CountryFilter filter, CriteriaQuery<CountryDao> cq, CriteriaBuilder cb) {
		Root<CountryDao> root = cq.from(getObjectClass());

		if (filter.getIsoCountryCode() != null) {
			cq.where(cb.equal(root.get("isoCountryCode"), filter.getIsoCountryCode()));
		}

		if (filter.getNameInDefaultLanguage() != null) {
			cq.where(cb.equal(root.get("nameInDefaultLanguage"), filter.getNameInDefaultLanguage()));
		}

		if (filter.getZipCodePrefix() != null) {
			cq.where(cb.equal(root.get("zipCodePrefix"), filter.getZipCodePrefix()));
		}

		return cq;
	}
}
