package com.github.orlin.socialCMS.database.filters;

import com.github.orlin.socialCMS.database.general.Filter;

public class CountryFilter extends Filter {
	private String nameInDefaultLanguage;
	private String isoCountryCode;
	private String zipCodePrefix;
	
	public CountryFilter() {}
	
	

	public CountryFilter(String nameInDefaultLanguage, String isoCountryCode,
			String zipCodePrefix) {
		super();
		this.nameInDefaultLanguage = nameInDefaultLanguage;
		this.isoCountryCode = isoCountryCode;
		this.zipCodePrefix = zipCodePrefix;
	}



	public final String getNameInDefaultLanguage() {
		return nameInDefaultLanguage;
	}
	public final void setNameInDefaultLanguage(String nameInDefaultLanguage) {
		this.nameInDefaultLanguage = nameInDefaultLanguage;
	}
	public final String getIsoCountryCode() {
		return isoCountryCode;
	}
	public final void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	public final String getZipCodePrefix() {
		return zipCodePrefix;
	}
	public final void setZipCodePrefix(String zipCodePrefix) {
		this.zipCodePrefix = zipCodePrefix;
	}
}
