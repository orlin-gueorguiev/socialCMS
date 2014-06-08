package com.github.orlin.socialCMS.database.filters;

import com.github.orlin.socialCMS.database.general.Filter;

public class WebPresenceFilter extends Filter {
	private String email;
	private String webpage;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
}
