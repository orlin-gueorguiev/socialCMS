package com.github.orlin.socialCMS.database.entities;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.orlin.socialCMS.database.general.DBEntity;

@Entity
@Table(name = "`Address`")
public class AddressDao extends DBEntity {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddressDao [id=");
		builder.append(id);
		builder.append(", country=");
		builder.append(country);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", street=");
		builder.append(street);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}


	private static final long serialVersionUID = 1L;
	private Long id;
	private CountryDao country;
	private Integer zipCode;
	private String street;
	private Calendar created, lastModified;
	
	

	public AddressDao(CountryDao country, Integer zipCode, String street) {
		this.country = country;
		this.zipCode = zipCode;
		this.street = street;
	}

	
	public AddressDao() {
		
	}


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	@JoinColumn(name="`countryId`", nullable=false)
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	public CountryDao getCountry() {
		return this.country;
	}

	
	public void setCountry(CountryDao country) {
		this.country = country;
	}

	
	@Column(name="`zipCode`", nullable=false)
	public Integer getZipCode() {
		return this.zipCode;
	}

	
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	
	@Column(name="street", nullable=false)
	public String getStreet() {
		return street;
	}

	
	public void setStreet(String street) {
		this.street = street;
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
