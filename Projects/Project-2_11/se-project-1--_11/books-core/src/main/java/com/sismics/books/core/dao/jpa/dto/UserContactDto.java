package com.sismics.books.core.dao.jpa.dto;

/**
 * User contact DTO.
 * 
 * @author jtremeaux
 */
public class UserContactDto {
    /**
     * User contact ID.
     */
    private String id;

    /**
     * External application contact ID.
     */
    private String externalId;

    /**
     * Contact's fullname.
     */
    private String fullName;

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
}
