package com.sismics.books.rest.model;

/**
 * Facebook test user.
 * 
 * Management : https://developers.facebook.com/apps/387037158089019/roles?role=test%20users
 * 
 * @author jtremeaux
 */
public class FacebookUser {
    public String id;
    
    public String email;
    
    public String fullName;

    public String accessToken;
    
    public FacebookUser(String id, String email, String fullName, String accessToken) {
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
        this.fullName = fullName;
    }
}

