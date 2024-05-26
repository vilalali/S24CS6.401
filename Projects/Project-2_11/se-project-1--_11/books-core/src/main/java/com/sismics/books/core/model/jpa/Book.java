package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;  
import javax.persistence.GeneratedValue; 
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType; 
import javax.persistence.GenerationType; 
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany; 
import java.util.List; 
import java.util.HashSet;
import javax.persistence.OneToMany;
import java.util.ArrayList;

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
    
    /**
     * Authors. Assuming authors are stored as a comma-separated values for simplicity.
     * In a normalized database design, this would be managed with a many-to-many relationship.
     */
    @Column(name = "BOK_AUTHORS_C", length = 1000) // Adjust length as needed
    private String authors;

    /**
     * Average Rating.
     */
    @Column(name = "BOK_RATING_N")
    private Float rating;

    /**
     * Genres. Assuming genres are stored as a comma-separated values for simplicity.
     * In a normalized database design, this would also be managed with a many-to-many relationship.
     
    @Column(name = "BOK_GENRES_C", length = 500) // Adjust length as needed
    private String genres; */

    @ManyToMany
    @JoinTable(
        name = "T_BOOK_GENRE", 
        joinColumns = @JoinColumn(name = "BOK_ID_C"), 
        inverseJoinColumns = @JoinColumn(name = "GEN_ID_C")
    )
    private Set<Genre> genres = new HashSet<>(); // Initialize the Set to avoid NullPointerException

    
    
  
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
     * Thumbnail image URL (optional).
     */
    @Column(name = "BOK_THUMBNAIL_URL_C", length = 2048)
    private String thumbnailImageUrl;
    
  
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
    
    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
    // Getters and Setters for the genres field
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
    
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }
    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .toString();
    }
}
