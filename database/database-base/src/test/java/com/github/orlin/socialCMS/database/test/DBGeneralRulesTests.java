package com.github.orlin.socialCMS.database.test;

import javax.persistence.EntityManager;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.CountryFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultCountryDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CountryDBService;

/**
 * A test class, which runs general tests, that should always run correctly, such as the automatic creation of a timestamp, etc.
 * @author orlin
 *
 */
public class DBGeneralRulesTests {
	EntityManager em = BasicTest.em;
	
	public CountryDBService countryService = new DefaultCountryDBService(em);

	

	@Test
	public void _01testTimestamp() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}

		em.getTransaction().begin();
		CountryDao country = countryService.create();
		country.setIsoCountryCode("TIMESTAMP");
		country.setNameInDefaultLanguage("TIMESTAMP");
		country.setZipCodePrefix("TIMESTAMP");
		countryService.save(country);
		em.getTransaction().commit();

		Assert.assertNotNull(country.getId(), "Country has no id!");

		CountryDao countryDao = countryService.loadByFilter(new CountryFilter("TIMESTAMP", "TIMESTAMP", "TIMESTAMP"));

		Assert.assertNotNull(countryDao.getId(), "Unable to load country after initial save");

		em.getTransaction().begin();
		countryDao.setNameInDefaultLanguage("TIMESTAMP_NEW");
		countryService.save(countryDao);
		em.getTransaction().commit();

		countryDao = countryService.loadByFilter(new CountryFilter("TIMESTAMP_NEW", "TIMESTAMP", "TIMESTAMP"));
		Assert.assertNotNull(countryDao.getId(), "Unable to load country after second save");

		Assert.assertNotEquals(countryDao.getCreated().getTime(), countryDao.getLastModified().getTime(),
				"We modified and saved the country, now the last saved should be different then created");
	}
}
