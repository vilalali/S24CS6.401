package com.sismics.books.core.dao.jpa.dto;

/**
 * User app DTO.
 * 
 * @author jtremeaux
 */
public class UserAppDto {
    /**
     * User app ID.
     */
    private String id;

    /**
     * User ID.
     */
    private String userId;

    /**
     * App ID.
     */
    private String appId;

    /**
     * OAuth access token.
     */
    private String accessToken;

    /**
     * Username of l'utilisateur dans l'application.
     */
    private String username;

    /**
     * Partage des tracés.
     */
    private boolean sharing;

    /**
     * Getter of id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of userId.
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter of userId.
     *
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter of appId.
     *
     * @return appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter of appId.
     *
     * @param appId appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter of accessToken.
     *
     * @return accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Setter of accessToken.
     *
     * @param accessToken accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter of username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of sharing.
     *
     * @return sharing
     */
    public boolean isSharing() {
        return sharing;
    }

    /**
     * Setter of sharing.
     *
     * @param sharing sharing
     */
    public void setSharing(boolean sharing) {
        this.sharing = sharing;
    }
}
