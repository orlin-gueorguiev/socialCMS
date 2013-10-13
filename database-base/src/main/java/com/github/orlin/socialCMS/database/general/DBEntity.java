package com.github.orlin.socialCMS.database.general;

import java.io.Serializable;
import java.util.Calendar;

public abstract class DBEntity implements Serializable {
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DBEntity [getId()=");
		builder.append(getId());
		builder.append(", getCreated()=");
		builder.append(getCreated());
		builder.append(", getLastModified()=");
		builder.append(getLastModified());
		builder.append("]");
		return builder.toString();
	}

	private static final long serialVersionUID = 1L;

	public abstract void setId(Long id);
	
	public abstract Long getId();
	
	public abstract Calendar getCreated();
	
	public abstract Calendar getLastModified();
}
