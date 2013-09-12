package com.github.orlin.socialCMS.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.github.orlin.socialCMS.database.general.DBEntity;

@Entity
@Table(name="`Person`")
public class PersonDao extends DBEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private CompanyDao company;
	
	public PersonDao() {}
	
	public PersonDao(AddressDao address, WebPresenceDao webPresence) {
		super();
		this.address = address;
		this.webPresence = webPresence;
	}

	private AddressDao address;
	private WebPresenceDao webPresence;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	
//	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="addressId")
	public AddressDao getAddress() {
		return this.address;
	}

	
	public void setAddress(AddressDao address) {
		this.address = address;
	}

	
//	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="`webpresenceId`")
	public WebPresenceDao getWebPresence() {
		return this.webPresence;
	}

	
	public void setWebPresence(WebPresenceDao webPresence) {
		this.webPresence = webPresence;
	}

	
	@Column(name="firstName")
	public String getFirstName() {
		return firstName;
	}

	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	@Column(name="lastName")
	public String getLastName() {
		return lastName;
	}

	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
//	@OneToMany(cascade={}, fetch=FetchType.LAZY)
	@JoinColumn(name="companyId")
	public CompanyDao getCompany() {
		return company;
	}

	
	public void setCompany(CompanyDao company) {
		this.company = company;
	}
}
