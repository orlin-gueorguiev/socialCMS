package com.github.orlin.socialCMS.database.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.orlin.socialCMS.database.general.DBEntity;

@Entity
@Table(name="`Company`")
public class CompanyDao extends DBEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private Long id;
	private List<PersonDao> contactPersons;
	private AddressDao address;
	private WebPresenceDao webPresence;
	private CompanyStatus status = CompanyStatus.NO_STATUS;
	
	
	/**
	 * The statuses, that a given company might have
	 * @author orlin
	 *
	 */
	public static enum CompanyStatus {
		/** No status set. This is the default. */
		NO_STATUS,
		
		/** Don't contact this Company */
		NOT_CONTACTABLE;
	};
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="`companyId`")
	public List<PersonDao> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<PersonDao> contactPersons) {
		this.contactPersons = contactPersons;
	}
	
	@Transient
	public void addContactPerson(PersonDao person) {
		if(this.getContactPersons() == null) {
			this.contactPersons = new LinkedList<PersonDao>();
		}
		
		this.contactPersons.add(person);
	}

	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="`addressId`")
	public AddressDao getAddress() {
		return this.address;
	}

	
	public void setAddress(AddressDao address) {
		this.address = address;
	}

	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="`webpresenceId`")
	public WebPresenceDao getWebPresence() {
		return this.webPresence;
	}

	
	public void setWebPresence(WebPresenceDao webPresence) {
		this.webPresence = webPresence;
	}

	
	public void removeContactPerson(PersonDao person) {
		this.getContactPersons().remove(person);
	}
	
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	public CompanyStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(CompanyStatus status) {
		this.status = status;
	}
}
