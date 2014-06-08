package com.github.orlin.socialCMS.portal.portlets.contact;

import javax.portlet.GenericPortlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.w3c.dom.Element;

public abstract class SocialCMSGenericPortlet extends GenericPortlet {

	public SocialCMSGenericPortlet() {
		super();
	}

	@Override
	protected void doHeaders(RenderRequest request, RenderResponse response) {
		super.doHeaders(request, response);
		addJS(response, "/socialCMSPortal-pa/js/jsonTables.js");
		addJS(response, "/socialCMSPortal-pa/js/jquery-1.9.1.js");
		addJS(response, "/socialCMSPortal-pa/js/jquery-ui-1.10.3.custom.min.js");
		
		addCSS(response, "/south-street/jquery-ui-1.10.3.custom.min.css");
	}

	/**
	 * Adds a javascript to the head of the html page (override doHeaders) 
	 * @param response the response
	 * @param jsUrl the url ot the JavaScript
	 */
	protected static void addJS(RenderResponse response, String jsUrl) {
		Element script = response.createElement("script");
		script.setAttribute("src", jsUrl);
		script.setAttribute("type", "text/javascript");
		
		response.addProperty(javax.portlet.MimeResponse.MARKUP_HEAD_ELEMENT, script);
	}
	
	/**
	 * Adds a css to the head of the html page (override doHeaders) 
	 * @param response the response
	 * @param cssURL the url ot the css
	 */
	protected static void addCSS(RenderResponse response, String cssURL) {
		Element link = response.createElement("link");
		link.setAttribute("rel", "stylesheet");
		link.setAttribute("href", cssURL);
		link.setAttribute("type", "text/javascript");
		
		response.addProperty(javax.portlet.MimeResponse.MARKUP_HEAD_ELEMENT, link);
	}

}