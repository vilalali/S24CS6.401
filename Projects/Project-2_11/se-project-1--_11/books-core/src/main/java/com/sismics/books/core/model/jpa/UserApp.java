package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * Link between a user and a connected application.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_USER_APP")
public class UserApp {
    /**
     * User app ID.
     */
    @Id
    @Column(name = "USA_ID_C", length = 36)
    private String id;
    
    /**
     * User ID.
     */
    @Column(name = "USA_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Connected pplication ID.
     */
    @Column(name = "USA_IDAPP_C", nullable = false, length = 20)
    private String appId;
    
    /**
     * OAuth access token.
     */
    @Column(name = "USA_ACCESSTOKEN_C", length = 255)
    private String accessToken;
    
    /**
     * User's username in the application.
     */
    @Column(name = "USA_USERNAME_C", length = 100)
    private String username;
    
    /**
     * User's ID in the application.
     */
    @Column(name = "USA_EXTERNALID_C", length = 50)
    private String externalId;
    
    /**
     * User's email in the application.
     */
    @Column(name = "USA_EMAIL_C", length = 100)
    private String email;
    
    /**
     * Share on this application.
     */
    @Column(name = "USA_SHARING_B", nullable = false)
    private boolean sharing;

    /**
     * Creation date.
     */
    @Column(name = "USA_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "USA_DELETEDATE_D")
    private Date deleteDate;

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
     * Getter of externalId.
     *
     * @return externalId
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Setter of externalId.
     *
     * @param externalId externalId
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
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
     * Getter of email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * Getter of createDate.
     *
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Setter of createDate.
     *
     * @param createDate createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter of deleteDate.
     *
     * @return deleteDate
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * Setter of deleteDate.
     *
     * @param deleteDate deleteDate
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("appId", appId)
                .toString();
    }
}
