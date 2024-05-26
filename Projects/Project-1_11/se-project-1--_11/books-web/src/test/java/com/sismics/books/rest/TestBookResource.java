package com.sismics.books.rest;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.google.common.io.ByteStreams;
import com.sismics.books.rest.filter.CookieAuthenticationFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * Test the book resource.
 * 
 * @author bgamard
 */
public class TestBookResource extends BaseJerseyTest {
    /**
     * Test the book resource.
     * 
     * @throws Exception 
     */
    @Test
    public void testBookResource() throws Exception {
        // Login book1
        clientUtil.createUser("book1");
        String book1Token = clientUtil.login("book1");
        
        // Create a tag
        WebResource tagResource = resource().path("/tag");
        tagResource.addFilter(new CookieAuthenticationFilter(book1Token));
        MultivaluedMapImpl postParams = new MultivaluedMapImpl();
        postParams.add("name", "Tag3");
        postParams.add("color", "#ff0000");
        ClientResponse response = tagResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONObject json = response.getEntity(JSONObject.class);
        String tag3Id = json.optString("id");
        Assert.assertNotNull(tag3Id);        
        
        // Add a book
        WebResource bookResource = resource().path("/book");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("isbn", "9780345376596");
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        String book1Id = json.optString("id");
        Assert.assertNotNull(book1Id);
        
        // Add the same book (KO)
        bookResource = resource().path("/book");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("isbn", "9780345376596");
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        
        // Update a book
        bookResource = resource().path("/book/" + book1Id);
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("tags", tag3Id);
        postParams.add("page_count", 362);
        postParams.add("author", "C. Sagan");
        postParams.add("isbn13", "9780345376596");
        response = bookResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // Create the same book manually (KO)
        bookResource = resource().path("/book/manual");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("title", "Fake title");
        postParams.add("author", "Fake author");
        postParams.add("publish_date", 42);
        postParams.add("tags", tag3Id);
        postParams.add("page_count", 300);
        postParams.add("author", "C. Sagan");
        postParams.add("isbn13", "9780345376596");
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        
        // Create a new book manually without ISBN (KO)
        bookResource = resource().path("/book/manual");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("title", "Fake title");
        postParams.add("author", "Fake author");
        postParams.add("publish_date", 42);
        postParams.add("tags", tag3Id);
        postParams.add("page_count", 300);
        postParams.add("author", "C. Sagan");
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        
        // Create a new book manually
        bookResource = resource().path("/book/manual");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("title", "Fake title");
        postParams.add("author", "Fake author");
        postParams.add("publish_date", 42);
        postParams.add("tags", tag3Id);
        postParams.add("page_count", 300);
        postParams.add("author", "C. Sagan");
        postParams.add("title", "Fake book");
        postParams.add("isbn13", "0123456789012");
        response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // Set a book as read
        bookResource = resource().path("/book/" + book1Id + "/read");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("read", true);
        response = bookResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // Update the book cover
        bookResource = resource().path("/book/" + book1Id + "/cover");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("url", "http://covers.openlibrary.org/b/id/976764-M.jpg");
        response = bookResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // Get the book
        bookResource = resource().path("/book/" + book1Id);
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        response = bookResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals("Pale Blue Dot", json.getString("title"));
        Assert.assertEquals("A Vision of the Human Future in Space", json.getString("subtitle"));
        Assert.assertEquals("C. Sagan", json.getString("author"));
        Assert.assertEquals(852073200000l, json.getLong("publish_date"));
        Assert.assertEquals(362, json.getLong("page_count"));
        Assert.assertTrue(json.getString("description").contains("the world beyond Earth"));
        Assert.assertEquals("en", json.getString("language"));
        Assert.assertEquals("0345376595", json.getString("isbn10"));
        Assert.assertEquals("9780345376596", json.getString("isbn13"));
        Assert.assertEquals("Tag3", json.getJSONArray("tags").getJSONObject(0).getString("name"));
        Assert.assertNotNull(json.getLong("read_date"));

        // Set a book as unread
        bookResource = resource().path("/book/" + book1Id + "/read");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        postParams = new MultivaluedMapImpl();
        postParams.add("read", false);
        response = bookResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // Get the book
        bookResource = resource().path("/book/" + book1Id);
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        response = bookResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        Assert.assertFalse(json.has("read_date"));
        
        // Get the book cover
        bookResource = resource().path("/book/" + book1Id + "/cover");
        response = bookResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        InputStream is = response.getEntityInputStream();
        byte[] fileBytes = ByteStreams.toByteArray(is);
        Assert.assertEquals(14951, fileBytes.length);
        
        // List all books
        bookResource = resource().path("/book/list");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        MultivaluedMapImpl getParams = new MultivaluedMapImpl();
        response = bookResource.queryParams(getParams).get(ClientResponse.class);
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONArray books = json.getJSONArray("books");
        Assert.assertTrue(books.length() == 2);
        
        // Search the book
        bookResource = resource().path("/book/list");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        getParams = new MultivaluedMapImpl();
        getParams.add("tag", "Tag3");
        getParams.add("search", "vision");
        getParams.add("read", false);
        response = bookResource.queryParams(getParams).get(ClientResponse.class);
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        books = json.getJSONArray("books");
        Assert.assertTrue(books.length() == 1);
        
        // Delete the book
        bookResource = resource().path("/book/" + book1Id);
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        response = bookResource.delete(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // List all books
        bookResource = resource().path("/book/list");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        getParams = new MultivaluedMapImpl();
        response = bookResource.queryParams(getParams).get(ClientResponse.class);
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        books = json.getJSONArray("books");
        Assert.assertTrue(books.length() == 1);
    }
    
    /**
     * Test the book import resource.
     * 
     * @throws Exception
     */
    @Test
    public void testBookImportResource() throws Exception {
        // Login bookimport1
        clientUtil.createUser("bookimport1");
        String bookImport1Token = clientUtil.login("bookimport1");
        
        // Import a Goodreads CSV
        WebResource bookResource = resource().path("/book/import");
        bookResource.addFilter(new CookieAuthenticationFilter(bookImport1Token));
        FormDataMultiPart form = new FormDataMultiPart();
        InputStream goodreadsImport = this.getClass().getResourceAsStream("/import/goodreads.csv");
        FormDataBodyPart fdp = new FormDataBodyPart("file",
                new BufferedInputStream(goodreadsImport),
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        form.bodyPart(fdp);
        ClientResponse response = bookResource.type(MediaType.MULTIPART_FORM_DATA).put(ClientResponse.class, form);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        
        // List all books
        bookResource = resource().path("/book/list");
        bookResource.addFilter(new CookieAuthenticationFilter(bookImport1Token));
        MultivaluedMapImpl getParams = new MultivaluedMapImpl();
        getParams.add("limit", 15);
        getParams.add("sort_column", 1);
        response = bookResource.queryParams(getParams).get(ClientResponse.class);
        JSONObject json = response.getEntity(JSONObject.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONArray books = json.getJSONArray("books");
        Assert.assertEquals(10, books.length());
        Assert.assertEquals("owned", books.getJSONObject(2).getJSONArray("tags").getJSONObject(0).getString("name"));
        
        // Get all tags
        WebResource tagResource = resource().path("/tag/list");
        tagResource.addFilter(new CookieAuthenticationFilter(bookImport1Token));
        response = tagResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        JSONArray tags = json.getJSONArray("tags");
        Assert.assertEquals(2, tags.length());
        Assert.assertEquals("owned", tags.getJSONObject(0).getString("name"));
    }
}