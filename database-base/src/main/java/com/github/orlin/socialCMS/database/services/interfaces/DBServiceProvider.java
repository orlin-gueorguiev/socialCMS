package com.github.orlin.socialCMS.database.services.interfaces;


public interface DBServiceProvider {
	AddressDBService getAddressService();
	
	CompanyDBService getCompanyService();
	
	CountryDBService getCountryService();
	
	WebPresenceDBService getWebpresenceService();
	
}
