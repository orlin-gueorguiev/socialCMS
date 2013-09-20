package com.github.orlin.socialCMS.database.test.services.impl.def;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;
import com.github.orlin.socialCMS.database.filters.CountryFilter;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultAddressDBService;
import com.github.orlin.socialCMS.database.services.impl.def.DefaultCountryDBService;
import com.github.orlin.socialCMS.database.services.interfaces.AddressDBService;
import com.github.orlin.socialCMS.database.services.interfaces.CountryDBService;
import com.github.orlin.socialCMS.database.test.BasicTest;

public class DefaultAddressDBServiceTest {
	public AddressDBService<AddressDao, AddressFilter> addressService = new DefaultAddressDBService();
	public CountryDBService<CountryDao, CountryFilter> countryService = new DefaultCountryDBService();
	
	EntityManager em = BasicTest.em;
	
	private int addressCount = 5;
	private List<AddressDao> createdAddresses = new LinkedList<>();
		
	@Test
	public void _01createCountry() {
		if(em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
		
		em.getTransaction().begin();
		CountryDao country = countryService.create();
		country.setIsoCountryCode("ISO1");
		country.setNameInDefaultLanguage("ISO1");
		country.setZipCodePrefix("ZIP1");
		countryService.save(country);
		em.getTransaction().commit();
		
		Assert.assertNotNull(country.getId(), "Country has no id!");
	}	
	
	@Test
	public void _10createAddress() {
		em.getTransaction().begin();
		CountryDao countryDao = countryService.loadByFilter(new CountryFilter(null, "ISO1", null));
		
		for(int i = 0; i < addressCount; i++) {
			AddressDao address = addressService.create();
			address.setCountry(countryDao);
			address.setStreet("STREET" + i);
			address.setZipCode(i);
			addressService.save(address);
			createdAddresses.add(address);
		}
		
		em.getTransaction().commit();
	}
	
	
	@Test
	public void _20loadAddress() {
		for(AddressDao addOrig : createdAddresses) {
			Assert.assertNotNull(addressService.load(addOrig.getId()).getStreet(), "Address/Street not loaded!");
			Assert.assertNotNull(addressService.load(addOrig.getId()).getCountry().getId(), "Country not loaded!");
		}
		
	}
	
	@Test
	public void _30loadByFilter() {
		CountryDao countryDao = countryService.loadByFilter(new CountryFilter(null, null, "ZIP1"));
		
		AddressFilter filter = new AddressFilter();
		filter.setCountry(countryDao);
		Collection<AddressDao> addresses = addressService.loadAllByFilter(filter);
		Assert.assertEquals(addresses.size(), addressCount, "We must have " + addressCount + " addresses for this country");
	}
	
	@Test void _31pagination() {
CountryDao countryDao = countryService.loadByFilter(new CountryFilter(null, null, "ZIP1"));
		
		AddressFilter filter = new AddressFilter();
		filter.setCountry(countryDao);
		filter.setCount(2);
		filter.setFirstResult(2);
		List<AddressDao> addresses = addressService.loadAllByFilter(filter);
		Assert.assertEquals(addresses.size(), 2, "We must have " + 2 + " addresses because of count");
		AddressDao add0 = addresses.get(0); 
		// setFirstResult = 2, we start from 0, so 0 and 1 are skipped, and our first winner is 2
		Assert.assertEquals(add0.getStreet(), "STREET2");
		AddressDao add1 = addresses.get(1);
		Assert.assertEquals(add1.getStreet(), "STREET3");
	}
	
	@Test
	public void _90deleteAddress() {
		em.getTransaction().begin();
		for(AddressDao saved : createdAddresses) {
			addressService.delete(saved);
		}
		
		CountryDao countryDao = countryService.loadByFilter(new CountryFilter("ISO1", "ISO1", "ZIP1"));
		
		countryService.delete(countryDao);
		em.getTransaction().commit();
		em.clear();		
	}
}
