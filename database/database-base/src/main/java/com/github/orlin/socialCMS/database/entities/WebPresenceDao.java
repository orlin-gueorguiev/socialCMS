package com.github.orlin.socialCMS.database.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.orlin.socialCMS.database.general.DBEntity;

@Entity
@Table(name="`WebPresence`")
public class WebPresenceDao extends DBEntity {
	public WebPresenceDao() {}
	
	public WebPresenceDao(String email, String webpage) {
		super();
		this.email = email;
		this.webpage = webpage;
	}

	private static final long serialVersionUID = 1L;
	private Long id;
	private String email;
	private String webpage;
	private Calendar created, lastModified;
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	
	@Column(name="webpage")
	public String getWebpage() {
		return webpage;
	}

	
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	@Override
	@Column(name="`created`", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreated() {
		return this.created;
	}


	@Override
	@Column(name="`lastModified`", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastModified() {
		return this.lastModified;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.lastModified = Calendar.getInstance();
	}

	protected void setCreated(Calendar date) {
		this.created = date;
	}
	
	protected void setLastModified(Calendar date) {
		this.lastModified = date;
	}
	
	@PrePersist
	public void preCreate() {
		Calendar c = Calendar.getInstance();
		this.lastModified = created = c; 
	}
}
