package com.github.orlin.socialCMS.rest.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.github.orlin.socialCMS.rest.CountryRestService.Country;
import com.github.orlin.socialCMS.rest.JaxBObject;

/**
 * The standard object, which is used when querying REST
 * @author orlin
 *
 */
@XmlRootElement
@XmlSeeAlso({JaxBObject.class, Country.class})
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

	@XmlAnyElement(lax=true)
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
