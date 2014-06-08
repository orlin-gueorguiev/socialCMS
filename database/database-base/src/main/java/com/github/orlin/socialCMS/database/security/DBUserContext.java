package com.github.orlin.socialCMS.database.security;


/**
 * Manages the logged in user
 * @author orlin
 *
 */
public class DBUserContext {
	private DBUser user;

	public DBUser getUser() {
		return user;
	}

	public void setUser(DBUser user) {
		this.user = user;
	}

}
