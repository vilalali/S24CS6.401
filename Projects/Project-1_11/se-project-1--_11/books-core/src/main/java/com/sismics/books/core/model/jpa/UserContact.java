package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * User's contact entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_USER_CONTACT")
public class UserContact {
    /**
     * Contact ID.
     */
    @Id
    @Column(name = "USC_ID_C", length = 36)
    private String id;
    
    /**
     * User ID.
     */
    @Column(name = "USC_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Connected application ID.
     */
    @Column(name = "USC_IDAPP_C", nullable = false, length = 20)
    private String appId;
    
    /**
     * Contact ID in the connected application.
     */
    @Column(name = "USC_EXTERNALID_C", nullable = false, length = 50)
    private String externalId;
    
    /**
     * Contact's fullname in the connected application.
     */
    @Column(name = "USC_FULLNAME_C", length = 100)
    private String fullName;
    
    /**
     * Contact's email in the connected application.
     */
    @Column(name = "USC_EMAIL_C", length = 100)
    private String email;
    
    /**
     * Creation date.
     */
    @Column(name = "USC_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Update date.
     */
    @Column(name = "USC_UPDATEDATE_D")
    private Date updateDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "USC_DELETEDATE_D")
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
     * Getter of fullName.
     *
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter of fullName.
     *
     * @param fullName fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * Getter of updateDate.
     *
     * @return updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Setter of updateDate.
     *
     * @param updateDate updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
