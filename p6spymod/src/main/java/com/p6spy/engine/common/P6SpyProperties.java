/*
 *
 * ====================================================================
 *
 * The P6Spy Software License, Version 1.1
 *
 * This license is derived and fully compatible with the Apache Software
 * license, see http://www.apache.org/LICENSE.txt
 *
 * Copyright (c) 2001-2002 Andy Martin, Ph.D. and Jeff Goke
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 * any, must include the following acknowlegement:
 * "The original concept and code base for P6Spy was conceived
 * and developed by Andy Martin, Ph.D. who generously contribued
 * the first complete release to the public under this license.
 * This product was due to the pioneering work of Andy
 * that began in December of 1995 developing applications that could
 * seamlessly be deployed with minimal effort but with dramatic results.
 * This code is maintained and extended by Jeff Goke and with the ideas
 * and contributions of other P6Spy contributors.
 * (http://www.p6spy.com)"
 * Alternately, this acknowlegement may appear in the software itself,
 * if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "P6Spy", "Jeff Goke", and "Andy Martin" must not be used
 * to endorse or promote products derived from this software without
 * prior written permission. For written permission, please contact
 * license@p6spy.com.
 *
 * 5. Products derived from this software may not be called "P6Spy"
 * nor may "P6Spy" appear in their names without prior written
 * permission of Jeff Goke and Andy Martin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package com.p6spy.engine.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**

 * @author jeff
 */
public class P6SpyProperties {
    // instance variables dealing with an instance of the properties file we pass around
    public Properties properties;

    /** Creates a new instance of P6SpyProperties 
     * @throws IOException */
    public P6SpyProperties() {
    	properties = new Properties();
    	try {
			properties.load(getPropertiesAsStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	private InputStream getPropertiesAsStream() {
		return this.getClass().getResourceAsStream("/spy.properties");
	}

    public void setClassValues(@SuppressWarnings("rawtypes") Class klass) {
        // only invoke this if the property file has been changed
        if (properties == null) {
            return;
        }

        // first load the properties from the property file
        List<String> allMethods = P6Util.findAllMethods(klass);
        for(String m : allMethods) {
            // lowercase and strip the end
            String methodName = m.substring(3);
            String value = (String)properties.get(methodName.toLowerCase());
            P6Util.dynamicSet(klass, "set"+methodName, value == null ? null : value.trim());
        }

        // next, check the environment and see if we should override any properties
        Collection<String> list = P6Util.findAllMethods(klass);

        for (String opt : list) {
            String value = System.getProperty("p6" + opt);

            if (value != null) {
                P6LogQuery.info("Found value in environment: "+opt+", setting to value: "+value);
                P6Util.dynamicSet(klass, opt,value);
            } else {
                P6LogQuery.info("No value in environment for: "+opt+", using: "+P6Util.dynamicGet(klass,opt));
            }
        }
    }

    public List<String> getOrderedList(String prefix) {
        List<String> orderedList = new ArrayList<String>();
        List<KeyValue> list = P6Util.loadProperties(new InputStreamReader(getPropertiesAsStream()), prefix);
        for (KeyValue keyValue : list) {
            String value = keyValue.getValue();
            orderedList.add(value);
        }
        return orderedList;
    }
}
