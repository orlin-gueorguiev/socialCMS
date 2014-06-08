package com.github.orlin.socialCMS.database.entities;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.orlin.socialCMS.database.general.DBEntity;

@Entity
@Table(name="`Person`")
public class PersonDao extends DBEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private CompanyDao company;
	private Calendar created, lastModified;
	private AddressDao address;
	private WebPresenceDao webPresence;
	
	public PersonDao() {}
	
	public PersonDao(AddressDao address, WebPresenceDao webPresence) {
		this.address = address;
		this.webPresence = webPresence;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="`addressId`")
	public AddressDao getAddress() {
		return this.address;
	}

	
	public void setAddress(AddressDao address) {
		this.address = address;
	}

	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="`webpresenceId`")
	public WebPresenceDao getWebPresence() {
		return this.webPresence;
	}

	
	public void setWebPresence(WebPresenceDao webPresence) {
		this.webPresence = webPresence;
	}

	
	@Column(name="`firstName`")
	public String getFirstName() {
		return firstName;
	}

	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	@Column(name="`lastName`")
	public String getLastName() {
		return lastName;
	}

	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@ManyToOne(optional = true, cascade={}, fetch=FetchType.LAZY)
	@JoinColumn(name="`companyId`", referencedColumnName = "id")
	public CompanyDao getCompany() {
		return company;
	}

	
	public void setCompany(CompanyDao company) {
		this.company = company;
	}
	
	@Override
	@Column(name="`created`")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreated() {
		return this.created;
	}


	@Override
	@Column(name="`lastModified`")
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
