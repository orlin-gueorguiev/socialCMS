package com.github.orlin.socialCMS.database.filters;

import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.general.Filter;

public class AddressFilter extends Filter {
	private CountryDao country;
	private Integer zipCodeMin;
	private Integer zipCodeMax;
	private Integer zipCodeExact;
	private String street;
	
	public CountryDao getCountry() {
		return country;
	}

	public void setCountry(CountryDao country) {
		this.country = country;
	}

	public Integer getZipCodeMin() {
		return zipCodeMin;
	}

	public void setZipCodeMin(Integer zipCodeMin) {
		this.zipCodeMin = zipCodeMin;
	}

	public Integer getZipCodeMax() {
		return zipCodeMax;
	}

	public void setZipCodeMax(Integer zipCodeMax) {
		this.zipCodeMax = zipCodeMax;
	}

	public Integer getZipCodeExact() {
		return zipCodeExact;
	}

	public void setZipCodeExact(Integer zipCodeExact) {
		this.zipCodeExact = zipCodeExact;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
