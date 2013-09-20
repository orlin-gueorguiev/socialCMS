package com.github.orlin.socialCMS.database.services.interfaces;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.entities.CompanyDao;
import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;
import com.github.orlin.socialCMS.database.filters.CompanyFilter;
import com.github.orlin.socialCMS.database.filters.CountryFilter;
import com.github.orlin.socialCMS.database.filters.WebPresenceFilter;

public interface DBServiceProvider {
	<T extends AddressDao, F extends AddressFilter>AddressDBService<T, F> getAddressService();
	
	<T extends CompanyDao, F extends CompanyFilter>CompanyDBService<T, F> getCompanyService();
	
	<T extends CountryDao, F extends CountryFilter>CountryDBService<T, F>getCountryService();
	
	<T extends WebPresenceDao, F extends WebPresenceFilter>WebPresenceDBService<T, F> getWebpresenceService();
	
}
