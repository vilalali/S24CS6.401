package com.sismics.books.rest;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sismics.books.rest.filter.CookieAuthenticationFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Test the tag resource.
 * 
 * @author bgamard
 */
public class TestTagResource extends BaseJerseyTest {
    /**
     * Test the tag resource.
     * 
     * @throws JSONException
     */
    @Test
    public void testTagResource() throws JSONException {
        // Login tag1
        clientUtil.createUser("tag1");
        String tag1Token = clientUtil.login("tag1");
        
        // Create a tag
        WebResource tagResource = resource().path("/tag");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        MultivaluedMapImpl postParams = new MultivaluedMapImpl();
        postParams.add("name", "Tag3");
        postParams.add("color", "#ff0000");
        ClientResponse response = tagResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONObject json = response.getEntity(JSONObject.class);
        String tag3Id = json.optString("id");
        Assert.assertNotNull(tag3Id);
        
        // Create a tag
        tagResource = resource().path("/tag");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("name", "Tag4");
        postParams.add("color", "#00ff00");
        response = tagResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        String tag4Id = json.optString("id");
        Assert.assertNotNull(tag4Id);
        
        // Create a tag with space (not allowed)
        tagResource = resource().path("/tag");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("name", "Tag 4");
        response = tagResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        
        // Create a book
        WebResource bookResource = resource().path("/book");
        bookResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("isbn", "9781468304930");
        postParams.add("tags", tag3Id);
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        
        // Create a book
        bookResource = resource().path("/book");
        bookResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("isbn", "0553293400");
        postParams.add("tags", tag4Id);
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        
        // Get all tags
        tagResource = resource().path("/tag/list");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        response = tagResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        JSONArray tags = json.getJSONArray("tags");
        Assert.assertTrue(tags.length() > 0);
        Assert.assertEquals("Tag4", tags.getJSONObject(1).getString("name"));
        Assert.assertEquals("#00ff00", tags.getJSONObject(1).getString("color"));
        
        // Update a tag
        tagResource = resource().path("/tag/" + tag4Id);
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("name", "UpdatedName");
        postParams.add("color", "#0000ff");
        response = tagResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals(tag4Id, json.getString("id"));
        
        // Get all tags
        tagResource = resource().path("/tag/list");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        response = tagResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        tags = json.getJSONArray("tags");
        Assert.assertTrue(tags.length() > 0);
        Assert.assertEquals("UpdatedName", tags.getJSONObject(1).getString("name"));
        Assert.assertEquals("#0000ff", tags.getJSONObject(1).getString("color"));
        
        // Deletes a tag
        tagResource = resource().path("/tag/" + tag4Id);
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        response = tagResource.delete(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals("ok", json.getString("status"));
        
        // Get all tags
        tagResource = resource().path("/tag/list");
        tagResource.addFilter(new CookieAuthenticationFilter(tag1Token));
        response = tagResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        tags = json.getJSONArray("tags");
        Assert.assertTrue(tags.length() == 1);
    }
}