package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * Book entity.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_BOOK")
public class Book {
    /**
     * Book ID.
     */
    @Id
    @Column(name = "BOK_ID_C", length = 36)
    private String id;
    
    /**
     * Title.
     */
    @Column(name = "BOK_TITLE_C", nullable = false, length = 255)
    private String title;
    
    /**
     * Subtitle.
     */
    @Column(name = "BOK_SUBTITLE_C", length = 255)
    private String subtitle;
    
    /**
     * Author.
     */
    @Column(name = "BOK_AUTHOR_C", nullable = false, length = 255)
    private String author;
    
    /**
     * Description.
     */
    @Column(name = "BOK_DESCRIPTION_C", length = 4000)
    private String description;
    
    /**
     * ISBN 10.
     */
    @Column(name = "BOK_ISBN10_C", length = 10)
    private String isbn10;
    
    /**
     * ISBN 13.
     */
    @Column(name = "BOK_ISBN13_C", length = 13)
    private String isbn13;
    
    /**
     * Page count.
     */
    @Column(name = "BOK_PAGECOUNT_N")
    private Long pageCount;
    
    /**
     * Language (ISO 639-1).
     */
    @Column(name = "BOK_LANGUAGE_C", length = 2)
    private String language;
    
    /**
     * Publication date.
     */
    @Column(name = "BOK_PUBLISHDATE_D", nullable = false)
    private Date publishDate;
    
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
     * Getter of title.
     * 
     * @return title
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
     * @return subtitle
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
     * @return author
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
     * Getter of description.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of description.
     * 
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of isbn10.
     * 
     * @return isbn10
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     * Setter of isbn10.
     * 
     * @param isbn10 isbn10
     */
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    /**
     * Getter of isbn13.
     * 
     * @return isbn13
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * Setter of isbn13.
     * 
     * @param isbn13 isbn13
     */
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     * Getter of pageCount.
     * 
     * @return pageCount
     */
    public Long getPageCount() {
        return pageCount;
    }

    /**
     * Setter of pageCount.
     * 
     * @param pageCount pageCount
     */
    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * Getter of language.
     * 
     * @return language
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
     * Getter of publishDate.
     * 
     * @return publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Setter of publishDate.
     * 
     * @param publishedDate publishDate
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .toString();
    }
}
