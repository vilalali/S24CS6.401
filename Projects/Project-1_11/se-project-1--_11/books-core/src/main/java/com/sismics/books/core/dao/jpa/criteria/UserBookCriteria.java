package com.sismics.books.core.dao.jpa.criteria;

import java.util.List;


/**
 * User book criteria.
 *
 * @author bgamard 
 */
public class UserBookCriteria {
    /**
     * User ID.
     */
    private String userId;
    
    /**
     * Search query.
     */
    private String search;
    
    /**
     * Read state.
     */
    private Boolean read;
    
    /**
     * Tag IDs.
     */
    private List<String> tagIdList;
    
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
     * Getter of search.
     *
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * Setter of search.
     *
     * @param search search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * Getter of tagIdList.
     *
     * @return the tagIdList
     */
    public List<String> getTagIdList() {
        return tagIdList;
    }

    /**
     * Setter of tagIdList.
     *
     * @param tagIdList tagIdList
     */
    public void setTagIdList(List<String> tagIdList) {
        this.tagIdList = tagIdList;
    }

    /**
     * Getter of read.
     * @return read
     */
    public Boolean getRead() {
        return read;
    }

    /**
     * Setter of read.
     * @param read read
     */
    public void setRead(Boolean read) {
        this.read = read;
    }
}
