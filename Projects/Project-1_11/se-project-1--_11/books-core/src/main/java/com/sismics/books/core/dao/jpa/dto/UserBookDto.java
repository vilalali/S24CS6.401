package com.sismics.books.core.dao.jpa.dto;

import javax.persistence.Id;

/**
 * User book DTO.
 *
 * @author bgamard 
 */
public class UserBookDto {
    /**
     * User book ID.
     */
    @Id
    private String id;
    
    /**
     * Title.
     */
    private String title;
    
    /**
     * Subtitle.
     */
    private String subtitle;
    
    /**
     * Author.
     */
    private String author;
    
    /**
     * Language (ISO 639-1).
     */
    private String language;
    
    /**
     * Publication date.
     */
    private Long publishTimestamp;
    
    /**
     * Creation date.
     */
    private Long createTimestamp;
    
    /**
     * Read date.
     */
    private Long readTimestamp;

    /**
     * Getter of id.
     *
     * @return the id
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
     * Getter of title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of title.
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter of subtitle.
     *
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Setter of subtitle.
     *
     * @param subtitle subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Getter of author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter of author.
     *
     * @param author author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter of language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter of language.
     *
     * @param language language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter of publishTimestamp.
     *
     * @return the publishTimestamp
     */
    public Long getPublishTimestamp() {
        return publishTimestamp;
    }

    /**
     * Setter of publishTimestamp.
     *
     * @param publishTimestamp publishTimestamp
     */
    public void setPublishTimestamp(Long publishTimestamp) {
        this.publishTimestamp = publishTimestamp;
    }

    /**
     * Getter of createTimestamp.
     *
     * @return the createTimestamp
     */
    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Setter of createTimestamp.
     *
     * @param createTimestamp createTimestamp
     */
    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * Getter of readTimestamp.
     *
     * @return the readTimestamp
     */
    public Long getReadTimestamp() {
        return readTimestamp;
    }

    /**
     * Setter of readTimestamp.
     *
     * @param readTimestamp readTimestamp
     */
    public void setReadTimestamp(Long readTimestamp) {
        this.readTimestamp = readTimestamp;
    }
}
