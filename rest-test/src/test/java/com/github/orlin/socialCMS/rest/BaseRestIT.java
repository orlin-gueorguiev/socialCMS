package com.github.orlin.socialCMS.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class BaseRestIT {
    public static WebResource generateWR(String restcall) {
        Client client = Client.create();
        WebResource wr = client.resource("http://localhost:8080/socialCRMRest/" + restcall);
        return wr;
    }

    public static String getIdFromXml(String xml) {

        Pattern idPattern = Pattern.compile(".*<id>(\\d+)</id>.*");

        Matcher matcher = idPattern.matcher(xml);
        Assert.assertTrue(matcher.matches());

        return matcher.group(1);
    }

}
