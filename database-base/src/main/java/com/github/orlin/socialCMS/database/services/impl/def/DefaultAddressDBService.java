package com.github.orlin.socialCMS.database.services.impl.def;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;
import com.github.orlin.socialCMS.database.services.interfaces.AddressDBService;

public class DefaultAddressDBService extends GenericDBService<AddressDao, AddressFilter> implements AddressDBService<AddressDao, AddressFilter> {
	private static final Logger log = Logger.getLogger(DefaultAddressDBService.class.toString());

	@Override
	public AddressDao create() {
		return new AddressDao();
	}

	@Override
	public Class<AddressDao> getObjectClass() {
		return AddressDao.class;
	}

	@Override
	public CriteriaQuery<AddressDao> generateCriteria(AddressFilter filter, CriteriaQuery<AddressDao> cq, CriteriaBuilder cb) {
		Root<AddressDao> address = cq.from(getObjectClass());

		if (filter.getCountry() != null) {
			Join<AddressDao, CountryDao> country = address.join("country");
			log.log(Level.ALL, "Adding countryID as an address filter: " + filter.getCountry().getId());
			cq.where(cb.equal(country.get("id"), filter.getCountry().getId()));
		}

		if (filter.getStreet() != null) {
			cq.where(cb.equal(address.get("street"), filter.getStreet()));
		}

		// handle zip code (exact/between min max/min/max)
		if (filter.getZipCodeExact() != null) {
			log.log(Level.FINEST, "Filtering Address with zip code = " + filter.getZipCodeExact());

			cq.where(cb.equal(address.get("zipCode"), filter.getZipCodeExact()));

			if (filter.getZipCodeMin() != null || filter.getZipCodeMax() != null) {
				log.log(Level.WARNING, "It seems that a zip code min/max was set, while searching for a specific zip code. "
						+ "We will search for the specific zip code, ignoring the min/max restrictions");
			}

		} else if (filter.getZipCodeMin() != null || filter.getZipCodeMax() != null) {
			if (filter.getZipCodeMin() != null && filter.getZipCodeMax() != null) {
				cq.where(cb.between(address.<Integer>get("zipCode"), filter.getZipCodeMin(), filter.getZipCodeMax()));
			} else {
				// handle when only min/max exists
				if (filter.getZipCodeMin() != null) {
					cq.where(cb.ge(address.<Integer>get("zipCode"), filter.getZipCodeMin()));
				}

				if (filter.getZipCodeMax() != null) {
					cq.where(cb.le(address.<Integer>get("zipCode"), filter.getZipCodeMax()));
				}
			}
		}

		return cq;
	}
}
