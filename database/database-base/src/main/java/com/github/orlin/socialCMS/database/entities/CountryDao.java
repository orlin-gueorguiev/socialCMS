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
@Table(name = "`Country`")
public class CountryDao extends DBEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nameInDefaultLanguage;
	private String zipCodePrefix;
	private String isoCountryCode;
	private Calendar created, lastModified;

	
	@Column(name="`nameInDefaultLanguage`", nullable=false)
	public String getNameInDefaultLanguage() {
		return nameInDefaultLanguage;
	}

	
	public void setNameInDefaultLanguage(String name) {
		this.nameInDefaultLanguage = name;
	}

	
	@Column(name="`zipCodePrefix`")
	public String getZipCodePrefix() {
		return zipCodePrefix;
	}

	
	public void setZipCodePrefix(String zipCodePrefix) {
		this.zipCodePrefix = zipCodePrefix;
	}

	
	@Column(name="`isoCountryCode`", nullable=false, unique=true)
	public String getIsoCountryCode() {
		return this.isoCountryCode;
	}

	
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	public String toString() {
		return "CountryDao [id=" + id + ", nameInDefaultLanguage="
				+ nameInDefaultLanguage + ", zipCodePrefix=" + zipCodePrefix
				+ ", isoCountryCode=" + isoCountryCode + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isoCountryCode == null) ? 0 : isoCountryCode.hashCode());
		result = prime
				* result
				+ ((nameInDefaultLanguage == null) ? 0 : nameInDefaultLanguage
						.hashCode());
		result = prime * result
				+ ((zipCodePrefix == null) ? 0 : zipCodePrefix.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryDao other = (CountryDao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isoCountryCode == null) {
			if (other.isoCountryCode != null)
				return false;
		} else if (!isoCountryCode.equals(other.isoCountryCode))
			return false;
		if (nameInDefaultLanguage == null) {
			if (other.nameInDefaultLanguage != null)
				return false;
		} else if (!nameInDefaultLanguage.equals(other.nameInDefaultLanguage))
			return false;
		if (zipCodePrefix == null) {
			if (other.zipCodePrefix != null)
				return false;
		} else if (!zipCodePrefix.equals(other.zipCodePrefix))
			return false;
		return true;
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
