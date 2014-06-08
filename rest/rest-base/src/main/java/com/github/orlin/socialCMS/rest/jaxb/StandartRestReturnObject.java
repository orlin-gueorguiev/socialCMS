package com.github.orlin.socialCMS.rest.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.github.orlin.socialCMS.rest.AddressRestService.Address;
import com.github.orlin.socialCMS.rest.CompanyRestService.Company;
import com.github.orlin.socialCMS.rest.CountryRestService.Country;
import com.github.orlin.socialCMS.rest.JaxBObject;
import com.github.orlin.socialCMS.rest.PersonRestService.Person;
import com.github.orlin.socialCMS.rest.WebPresenceRestService.WebPresence;

/**
 * The standard object, which is used when querying REST
 * @author orlin
 *
 */
@XmlRootElement
@XmlSeeAlso({JaxBObject.class, Country.class, Address.class, WebPresence.class, Person.class, Company.class})
public class StandartRestReturnObject<J extends JaxBObject> {
	private Long totalObjects;
	
	private List<J> queriedObject;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StandartRestReturnObject [totalObjects=");
		builder.append(totalObjects);
		builder.append(", queriedObject=");
		builder.append(queriedObject);
		builder.append("]");
		return builder.toString();
	}

	@XmlElement(name="data")
	public List<J> getQueriedObject() {
		return queriedObject;
	}

	public void setQueriedObject(List<J> queriedObject) {
		this.queriedObject = queriedObject;
	}

	public Long getTotalObjects() {
		return totalObjects;
	}

	public void setTotalObjects(Long totalObjects) {
		this.totalObjects = totalObjects;
	}
}
