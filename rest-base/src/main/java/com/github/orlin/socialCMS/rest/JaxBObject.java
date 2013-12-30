package com.github.orlin.socialCMS.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Marker class for all JaxB objects, needed for JaxB, since if we use java.lang.Object,
 * the marshaling will fail
 * @author orlin
 *
 */
@XmlRootElement(name="data")
public class JaxBObject {

}
